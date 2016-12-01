package com.pespl.order.mgmt.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pespl.order.mgmt.po.PurchaseRequest;

public class Invoice {

	private Integer id;
	private String number;
	private Date eta;
	private Date dateCreated;
	private Date dateUpdated;
	private List<DeliveredItem> deliveredItems;
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private List<PurchaseRequest> deliveredRequests;
	private Date date;

	public Invoice() {
		deliveredItems = new ArrayList<>();
		deliveredRequests = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
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

	public List<DeliveredItem> getDeliveredItems() {
		return deliveredItems;
	}

	public void setDeliveredItems(List<DeliveredItem> deliveredItems) {
		this.deliveredItems = deliveredItems;
	}

	public void addDeliveredItem(DeliveredItem deliveredItem) {
		this.deliveredItems.add(deliveredItem);
	}

	public void addDeliveredRequest(PurchaseRequest deliveredRequest) {
		deliveredRequests.add(deliveredRequest);
	}

	public List<PurchaseRequest> getDeliveredRequests() {
		return deliveredRequests;
	}

	public void setDeliveredRequests(List<PurchaseRequest> deliveredRequests) {
		this.deliveredRequests = deliveredRequests;
	}
}
