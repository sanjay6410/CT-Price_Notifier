package gcfv2pubsub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductVariant;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListLineItem;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;
import com.commercetools.api.models.type.CustomFields;
import com.commercetools.api.models.shopping_list.ShoppingListUpdate;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.events.cloud.pubsub.v1.MessagePublishedData;

import com.google.gson.Gson;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cloudevents.CloudEvent;

import java.util.Base64;
import java.util.logging.Logger;

import java.util.Properties;
import javax.mail.Session;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;


public class PubSubFunction implements CloudEventsFunction {
    
    private static final Logger logger = Logger.getLogger(PubSubFunction.class.getName());

    public ProjectApiRoot createApiClient() {
        final ProjectApiRoot apiRoot = ApiRootBuilder.of()
                .defaultClient(
                        ClientCredentials.of()
                                .withClientId("VTV_8r0J4v1JO-SCrDAiYUmF")
                                .withClientSecret("iG5z-j-SS4sbi6ex93PJBHSR_RXQlgcN")
                                .build(),
                        ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
                .build("price-notifier");

        return apiRoot;
    }

    @Override
    public void accept(CloudEvent event) {
        // Get cloud event data as JSON string
        String cloudEventData = new String(event.getData().toBytes());
        // Decode JSON event data to the Pub/Sub MessagePublishedData type
        Gson gson = new Gson();
        MessagePublishedData data = gson.fromJson(cloudEventData, MessagePublishedData.class);
        // Get the message from the data
        com.google.events.cloud.pubsub.v1.Message message = data.getMessage();
        // Get the base64-encoded data from the message & decode it
        String encodedData = message.getData();
        String decodedData = new String(Base64.getDecoder().decode(encodedData));
        // Log the message
        logger.info("Pub/Sub message: " + decodedData);
        JsonObject json = JsonParser.parseString(decodedData).getAsJsonObject();
        String productId = json.getAsJsonObject("resource").get("id").getAsString();
        Long variantId = json.get("variantId").getAsLong();
        logger.info("Product ID: " + productId);
        ProjectApiRoot par = createApiClient();
        String priceId=json.getAsJsonObject("oldPrice").get("id").getAsString();
        // Original price
        Long originalPrice = json.getAsJsonObject("oldPrice").getAsJsonObject("value").get("centAmount").getAsLong();

        // Updated price
        Long updatedPrice = json.getAsJsonObject("newPrice").getAsJsonObject("value").get("centAmount").getAsLong();

        // Calculate the reduced percentage
        Long reducedPercentage = Math.round((originalPrice - updatedPrice) / (double) originalPrice * 100);
        
        // Retrieve the product name
        JsonObject resourceUserProvidedIdentifiers = json.getAsJsonObject("resourceUserProvidedIdentifiers");
        JsonObject slug = resourceUserProvidedIdentifiers.getAsJsonObject("slug");
        String productName = slug.get("en").getAsString();

        // Log the reduced percentage
        logger.info("Reduced Percentage: " + reducedPercentage + "%");

        String id = productId; // Assuming you have the variantId available as well
        List<String> customerIds = new ArrayList<>();
     
        List<Customer> customers = new ArrayList<>();

        ShoppingListPagedQueryResponse shoppingListResponse = par.shoppingLists().get()
                .withWhere("lineItems(productId=:productId) and lineItems(variantId=:variantId)")
                .withPredicateVar("productId", id)
                .withPredicateVar("variantId", variantId)
                .executeBlocking()
                .getBody();
        Product product=par.products().withId(id).get().executeBlocking().getBody();
        List<Price> productPricesMasterVariant = product.getMasterData().getCurrent().getMasterVariant().getPrices();
	    List<ProductVariant> productVariants = product.getMasterData().getCurrent().getAllVariants();
	    List<Price> variantPrices = new ArrayList<>();
	        for (ProductVariant productVariant : productVariants) {
	            variantPrices.addAll(productVariant.getPrices());
	        }
	        variantPrices.addAll(productPricesMasterVariant);

	     
	    List<Price> matchedVariants=new ArrayList<>();
	     for (Price price : variantPrices) {
	    	    if( price.getCountry()==null && price.getId().equals(priceId) && price.getCustomerGroup()==null) {
	    	       logger.info(" matched");
	    	        matchedVariants.add(price);
	    	       
	    	 break;
            }
             else {
	    	       logger.info("dint match the price");
	    	        // Perform actions for the unmatched price
	    	    } 
         }  
         for(Price matchedVariant:matchedVariants){
        // Iterate through each shopping list
        for (ShoppingList shoppingList : shoppingListResponse.getResults()) {
            customerIds.add(shoppingList.getCustomer().getId());
            logger.info("id of customer:" + shoppingList.getCustomer().getId());
            List<ShoppingListLineItem> foundLineItems = new ArrayList<>();
            for (ShoppingListLineItem lineItem : shoppingList.getLineItems()) {
                // Check if the line item matches the productId and variantId
                if (lineItem.getProductId().equals(productId) && lineItem.getVariantId().equals(variantId)) {
                    foundLineItems.add(lineItem);
                }
            }
           
            logger.info("ids...." + customerIds);
            for (ShoppingListLineItem foundLineItem : foundLineItems) {
                if (foundLineItem != null) {
                    // Retrieve the custom fields
                    CustomFields customFields = foundLineItem.getCustom();

                    // Retrieve the "Percentage-Number" field
                    Map<String, Object> percent = customFields.getFields().values();

                    // Retrieve the percentage number
                    Long percentageNumber = (Long) percent.get("Percentage-Number");
                    String lineItemId = foundLineItem.getId();
                    
                    // Retrieve the price check status
                    String priceCheckStatus = (String) percent.get("Price-Check-Status");
                    logger.info("Price Check Status: " + priceCheckStatus);

                    if (percentageNumber != null && priceCheckStatus != null && percentageNumber <= reducedPercentage && priceCheckStatus.equals("Active")) {
                        for (String customerId : customerIds) {
                            Customer customer = par.customers().withId(customerId).get().executeBlocking().getBody();
                            customers.add(customer);
                            logger.info("customer: " + customer.getFirstName());
                        }

                        for (Customer customer : customers) {
                            logger.info("Send mail to customer: " + customer.getEmail());
                            sendEmailPriceDrop(customer.getEmail(), productName, customer.getFirstName(), originalPrice, updatedPrice, reducedPercentage);
                            
                             ShoppingList list=par.shoppingLists().withId(shoppingList.getId()).get().executeBlocking().getBody();
		            ShoppingListUpdate listUpdate=ShoppingListUpdate.builder()
		            		   .version(list.getVersion())
		            		   .plusActions(t -> t.setLineItemCustomFieldBuilder()
		            				   .lineItemId(lineItemId)
		            				   
		            				   .name("Price-Check-Status").value("inActive"))
		            		   .build();
                            
                            ShoppingList updatedList = par.shoppingLists().withId(shoppingList.getId()).post(listUpdate).executeBlocking().getBody();
                            logger.info("ID of inactivated shopping list: " + updatedList.getId());
                        }
                    } else {
                        logger.info("Don't send mail");
                    }
                }
            }

             
	    	}
           
         }
    }



    public void sendEmailPriceDrop(String toEmail, String productName, String customerName, Long oldPrice,Long newPrice,Long reducedPercentage){
		Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with the hostname of your SMTP server
    props.put("mail.smtp.port", "587"); // Replace with the port number of your SMTP server
    props.put("mail.smtp.auth", "true"); // If authentication is required
    props.put("mail.smtp.starttls.enable", "true");
     Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("pricenotifier0607@gmail.com", "pgdokbfjzywbmnxb"); // Replace with your SMTP server credentials
        }
    });

    try {
       javax.mail.Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("pricenotifier0607@gmail.com"));
      msg.addRecipient( javax.mail.Message.RecipientType.TO,
                   new InternetAddress(toEmail));
    msg.setSubject("Price Drop Notification :" + productName);
    String template = "Dear " + customerName + ",\n\n" +
        "I hope this email finds you well. I am pleased to inform you that there has been a significant price drop on the " + productName + " you recently showed interest in. The price has been reduced by " + reducedPercentage + "%, resulting in exciting new savings for you.\n\n" +
        "Here are the details of the price drop:\n\n" +
        "Product Name: " + productName + "\n" +
        "Old Price: " + oldPrice + "\n" +
        "New Price: " + newPrice + "\n\n" +
        "You can view your shopping list and make a purchase by clicking on the following link: " + "http://localhost:3000/showShoppingList" + "\n\n" +
        "We understand that purchasing decisions are influenced by various factors, including price. Therefore, we wanted to ensure that you are aware of this attractive price reduction. We believe this presents a great opportunity for you to acquire the " + productName + " at a highly competitive price.\n\n" +
        "Should you have any questions or require further assistance, please feel free to reach out to our dedicated customer support team. We are here to assist you and provide any additional information you may need.\n\n" +
        "Thank you for choosing our brand, and we look forward to serving you in the future.\n\n" +
        "Best regards,\n" +
        "Team CT-Poc\n" +
        "Valtech\n" +
        "Email: pricenotifier0607@gmail.com";

  msg.setText(template);

  Transport.send(msg);
} catch (AddressException e) {
  logger.info("exceptin" +e);
} catch (MessagingException e) {
   logger.info("exceptin" +e);
} 
		
}
}


