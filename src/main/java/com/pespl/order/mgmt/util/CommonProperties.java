package com.pespl.order.mgmt.util;

import java.util.ResourceBundle;

public class CommonProperties {
	private static final ResourceBundle commonProperties = ResourceBundle.getBundle("common");

	public static String getValue(String key) {
		return commonProperties.getString(key);
	}

}
