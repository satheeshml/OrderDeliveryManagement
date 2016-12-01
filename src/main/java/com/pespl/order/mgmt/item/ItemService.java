package com.pespl.order.mgmt.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	@Autowired
	private ItemDAO dao;

	public void insert(Item item) {
		dao.insert(item);
	}

	public List<Item> list() {
		return dao.list();
	}
	
	public Item get(Integer id) {
		return dao.get(id);
	}
	
	public void changePrice(Price price){
		dao.changePrice(price);
	}
	public List<Currency> listCurrency(){
		return dao.listCurrency();
	}
	public Boolean isExist(String customerPart,String supplierPart,String name){
		return dao.isExist(customerPart, supplierPart, name);
	}
	
	public List<Item> getPriceHistoryReport(){
		return dao.getPriceHistory();
	}
}
