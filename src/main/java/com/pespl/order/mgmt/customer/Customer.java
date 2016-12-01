package com.pespl.order.mgmt.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pespl.order.mgmt.item.Item;

public class Customer {

	private Integer id;
	private String name;
	private Customer parent;
	private Date dateCreated;
	private Date dateUpdated;
	List<Item> itemList;

	public Customer(){
		itemList = new ArrayList<>();
	}
	
	public void addItem(Item item){
		itemList.add(item);
	}
	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getParent() {
		return parent;
	}

	public void setParent(Customer parent) {
		this.parent = parent;
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

}
