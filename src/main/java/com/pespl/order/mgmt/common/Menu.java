package com.pespl.order.mgmt.common;

public enum Menu {

	PURCHASE_ORDER("Purchase Order", "purchaseOrder"),
	INVOICE("Invoice","invoice"),
	ITEM("Item","item"),
	MODE("Mode","shipment");


	private String name;
	private String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	private Menu(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
