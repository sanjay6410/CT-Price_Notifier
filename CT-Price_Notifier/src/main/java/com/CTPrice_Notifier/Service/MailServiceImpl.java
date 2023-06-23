package com.CTPrice_Notifier.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {

	@Autowired
	private JavaMailSender mailSender;

	public void sendSampleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pricenotifier0607@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);

		mailSender.send(message);
		System.out.println("mail sent....");
	}

	public void sendEmailPriceDrop(String toEmail, String productName, String customerName, Long oldPrice,
			Long newPrice,Long reducedPercentage) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pricenotifier0607@gmail.com");
		message.setTo(toEmail);
		message.setSubject("Price Drop Notification :" + productName);
		String template = "Dear " + customerName + ",\n\n" +
		        "I hope this email finds you well. I am pleased to inform you that there has been a significant price drop on the " + productName + " you recently showed interest in. The price has been reduced by " + reducedPercentage + "%, resulting in exciting new savings for you.\n\n" +
		        "Here are the details of the price drop:\n\n" +
		        "Product Name: " + productName + "\n" +
		        "Old Price: " + oldPrice + "\n" +
		        "New Price: " + newPrice + "\n\n" +
		        "We understand that purchasing decisions are influenced by various factors, including price. Therefore, we wanted to ensure that you are aware of this attractive price reduction. We believe this presents a great opportunity for you to acquire the " + productName + " at a highly competitive price.\n\n" +
		        "Should you have any questions or require further assistance, please feel free to reach out to our dedicated customer support team. We are here to assist you and provide any additional information you may need.\n\n" +
		        "Thank you for choosing our brand, and we look forward to serving you in the future.\n\n" +
		        "Best regards,\n" +
		        "Team CT-Poc\n" +
		        "Valtech\n" +
		        "Email:pricenotifier0607@gmail.com";
		message.setText(template);
		mailSender.send(message);
		System.out.println("Mail Sent .........");


	}
}
