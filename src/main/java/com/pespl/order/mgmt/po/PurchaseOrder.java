package com.pespl.order.mgmt.po;

import java.util.ArrayList;
import java.util.List;

import com.pespl.order.mgmt.customer.Customer;

public class PurchaseOrder {
	private Integer id;
	private Long number;
	private Customer customer;

	private List<PurchaseRequest> purchaseRequests;

	public PurchaseOrder(){
		purchaseRequests = new ArrayList<>();
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<PurchaseRequest> getPurchaseRequests() {
		return purchaseRequests;
	}

	public void setPurchaseRequests(List<PurchaseRequest> purchaseRequests) {
		this.purchaseRequests = purchaseRequests;
	}

	public void addPurchaseRequests(PurchaseRequest purchaseRequest) {
		this.purchaseRequests.add(purchaseRequest);
	}

}
