package com.CTPrice_Notifier.Model;


public class ProductModel {

	    private ProductType productType;
	    private String name;
	    private	String slug;
	    private String Sku;
	    private String variantKey;
	    private String description;
	    public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getSku() {
			return Sku;
		}

		public void setSku(String sku) {
			Sku = sku;
		}

		public String getVariantKey() {
			return variantKey;
		}

		public void setVariantKey(String variantKey) {
			this.variantKey = variantKey;
		}

		public String getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}

		public Long getCentAmount() {
			return centAmount;
		}

		public void setCentAmount(Long centAmount) {
			this.centAmount = centAmount;
		}


		private String currencyCode;
        private Long centAmount;

	   

	    public ProductType getProductType() {
			return productType;
		}

		public void setProductType(ProductType productType) {
			this.productType = productType;
		}



		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}

		
		public static class ProductType {
	        private String id;
	        private String typeId;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getTypeId() {
				return typeId;
			}
			public void setTypeId(String typeId) {
				this.typeId = typeId;
			}

	       
	    }

	 


	

}
