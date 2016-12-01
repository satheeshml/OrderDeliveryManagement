package com.pespl.order.mgmt.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;

@Component
public class CustomerDAO extends BaseDAO {

	public void insert(final Customer customer) {
		Object params[] = new Object[3];
		params[0] = customer.getName();
		params[1] = customer.getParent().getId();
		params[2] = customer.getDateCreated();
		this.template.update("insert into customer(name,parent_id,date_created) values(?,?,?)", params);
	}

	public List<Customer> list() {
		return this.template.query("select * from customer", new CustomerExtractor());
	}

	private class CustomerExtractor implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getInt("id"));
			customer.setName(rs.getString("name"));
			Customer parent = new Customer();
			parent.setId(rs.getInt("parent_id"));
			customer.setParent(parent);
			customer.setDateCreated(rs.getDate("date_created"));
			return customer;
		}
	}
}
