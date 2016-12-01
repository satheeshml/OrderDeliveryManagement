package com.pespl.order.mgmt.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pespl.order.mgmt.delivery.DeliveredItem;
import com.pespl.order.mgmt.delivery.ShipmentMode;

public class PurchaseRequest {

	private Integer id;
	private PurchaseOrder purchaseOrder;
	private ShipmentMode mode;
	private Date dateCreated;
	private Date dateUpdated;
	private Date purchaseOrderDate;
	private Date proformaInvoiceDate;
	private List<PurchaseItem> purchaseItems;

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	private List<DeliveredItem> deliveredItems;

	public PurchaseRequest() {
		purchaseItems = new ArrayList<>();
		deliveredItems = new ArrayList<>();
	}

	public void addPurchaseItem(PurchaseItem purchaseItem) {
		purchaseItems.add(purchaseItem);
	}

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public void addDeliveredItem(DeliveredItem deliveredItem) {
		deliveredItems.add(deliveredItem);
	}

	public List<DeliveredItem> getDeliveredItems() {
		return deliveredItems;
	}

	public void setDeliveredItems(List<DeliveredItem> deliveredItems) {
		this.deliveredItems = deliveredItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public ShipmentMode getMode() {
		return mode;
	}

	public void setMode(ShipmentMode mode) {
		this.mode = mode;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Date getProformaInvoiceDate() {
		return proformaInvoiceDate;
	}

	public void setProformaInvoiceDate(Date proformaInvoiceDate) {
		this.proformaInvoiceDate = proformaInvoiceDate;
	}

}
