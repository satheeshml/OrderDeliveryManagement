package com.pespl.order.mgmt.delivery;

import com.pespl.order.mgmt.item.Item;
import com.pespl.order.mgmt.po.PurchaseRequest;

public class DeliveredItem {

	private Integer id;
	private PurchaseRequest purchaseRequest;
	private Item item;
	private Integer quantity;
	private Integer orderedQuantity;
	private Invoice invoice;

	public Integer getId() {
		return id;
	}

	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
