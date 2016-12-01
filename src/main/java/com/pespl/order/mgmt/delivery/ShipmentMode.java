package com.pespl.order.mgmt.delivery;

import java.io.Serializable;

public class ShipmentMode implements Serializable {

	private static final long serialVersionUID = 7727836197469106527L;

	private Integer id;
	private String mode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
