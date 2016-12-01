package com.pespl.order.mgmt.item;

import java.util.Date;

public class Price {
	private Integer id;
	private Integer itemId;
	private Float price;
	private Date asOn;
	private Currency currency;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getAsOn() {
		return asOn;
	}

	public void setAsOn(Date asOn) {
		this.asOn = asOn;
	}
}
