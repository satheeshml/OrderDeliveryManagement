package com.pespl.order.mgmt.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pespl.order.mgmt.customer.Customer;

public class Item {

	private Integer id;
	private String customerPart;
	private String supplierPart;
	private String name;
	private Date created;
	private Date updated;
	private List<Customer> customerList;
	private Price price;
	private List<Price> priceHistory;

	public Item() {
		customerList = new ArrayList<>();
		priceHistory = new ArrayList<>();
	}

	
	public void addPrice(Price price){
		priceHistory.add(price);
	}
	public List<Price> getPriceHistory() {
		return priceHistory;
	}

	public void setPriceHistory(List<Price> priceHistory) {
		this.priceHistory = priceHistory;
	}

	
	public void addCustomer(Customer customer) {
		customerList.add(customer);
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerPart() {
		return customerPart;
	}

	public void setCustomerPart(String customerPart) {
		this.customerPart = customerPart;
	}

	public String getSupplierPart() {
		return supplierPart;
	}

	public void setSupplierPart(String supplierPart) {
		this.supplierPart = supplierPart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public boolean equals(Object o){
		Item item = (Item) o;
		return (item!=null && item.getId()==this.id);
	}

}
