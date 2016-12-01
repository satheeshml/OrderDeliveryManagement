package com.pespl.order.mgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDAO 
{
	@Autowired
	protected JdbcTemplate template;
	
	
}
