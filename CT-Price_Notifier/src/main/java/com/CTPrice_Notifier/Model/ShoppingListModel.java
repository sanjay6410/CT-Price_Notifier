package com.CTPrice_Notifier.Model;

public class ShoppingListModel {

	private String name;
	private String description;
	private Long quantity;
	private int percentageNumber;
	public int getPercentageNumber() {
		return percentageNumber;
	}
	public void setPercentageNumber(int percentageNumber) {
		this.percentageNumber = percentageNumber;
	}
	public ShoppingListModel(String name, String description, Long quantity, int percentageNumber) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.percentageNumber = percentageNumber;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public ShoppingListModel(String name, String description, Long quantity) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
	}
	public ShoppingListModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
