package com.pespl.order.mgmt.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	CustomerDAO dao;

	public void insert(Customer customer) {
		dao.insert(customer);
	}
	
	public List<Customer> list(){
		return dao.list();
	}

}
