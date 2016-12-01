package com.pespl.order.mgmt.item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;
import com.pespl.order.mgmt.customer.Customer;

@Component
public class ItemDAO extends BaseDAO {

	public synchronized void insert(Item item) {
		int itemId = this.template.queryForInt("select max(id)  from item") + 1;
		Price price = item.getPrice();
		this.template.update("insert into item(id,name,customer_part,supplier_part,date_created) values(?,?,?,?,?)",
				new Object[] { itemId, item.getName(), item.getCustomerPart(), item.getSupplierPart(),
						item.getCreated() });
		this.template.update("insert into price(item_id,price,currency_id,asOn) values(?,?,?,?)",
				new Object[] { itemId, price.getPrice(), price.getCurrency().getId(), price.getAsOn() });
		for (Customer customer : item.getCustomerList()) {
			this.template.update("insert into item_access_customer(customer_id,item_id) values(?,?)",
					new Object[] { customer.getId(), itemId });
		}
	}

	public List<Item> list() {
		return this.template.query(
				"select i.id as item_id,i.name,i.customer_part,i.supplier_part,i.date_created,p.id as price_id,p.price from item i,price p where i.id=p.item_id and p.id= (select max(pr.id) from price pr where pr.item_id=i.id)",
				new ItemListExtractor());
	}

	public Boolean isExist(String customerPart, String supplierPart, String name) {
		Item item = this.template.query(
				"select i.id as item_id,i.name,i.customer_part,i.supplier_part,i.date_created,0 as price_id,0 as price from item i where i.customer_part=? and i.supplier_part=? and i.name=?",
				new Object[] { customerPart, supplierPart, name }, new ItemExtractor());
		return (item != null && item.getId() != null);
	}

	public Item get(Integer id) {
		return this.template.query(
				"select i.id as item_id,i.name,i.customer_part,i.supplier_part,i.date_created,p.id as price_id,p.price from item i,price p where i.id=p.item_id and i.id=?",
				new Object[] { id }, new ItemExtractor());
	}

	private class ItemExtractor implements ResultSetExtractor<Item> {

		public Item extractData(ResultSet rs) throws SQLException, DataAccessException {
			Item item = new Item();
			if (rs.next()) {
				item.setId(rs.getInt("item_id"));
				item.setCustomerPart(rs.getString("customer_part"));
				item.setSupplierPart(rs.getString("supplier_part"));
				item.setName(rs.getString("name"));
				Price price = new Price();
				price.setPrice(rs.getFloat("price"));
				price.setId(rs.getInt("price_id"));
				item.setPrice(price);
			}
			return item;
		}

	}

	private class ItemListExtractor implements RowMapper<Item> {
		@Override
		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
			Item item = new Item();
			item.setId(rs.getInt("item_id"));
			item.setName(rs.getString("name"));
			item.setCustomerPart(rs.getString("customer_part"));
			item.setSupplierPart(rs.getString("supplier_part"));
			item.setCreated(rs.getDate("date_created"));
			Price price = new Price();
			price.setId(rs.getInt("price_id"));
			price.setPrice(rs.getFloat("price"));
			item.setPrice(price);
			return item;
		}
	}

	public void changePrice(Price price) {
		this.template.update("insert into price(item_id,price,currency_id,asOn) values(?,?,?,?)",
				new Object[] { price.getItemId(), price.getPrice(), price.getCurrency().getId(), price.getAsOn() });
	}

	public List<Currency> listCurrency() {
		return this.template.query("select * from currency", new CurrencyListExtractor());
	}

	private class CurrencyListExtractor implements RowMapper<Currency> {

		@Override
		public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
			Currency currency = new Currency();
			currency.setId(rs.getInt("id"));
			currency.setName(rs.getString("name"));
			currency.setCountry(rs.getString("country"));
			return currency;
		}

	}

	public List<Item> getPriceHistory() {
		return this.template.query(
				"select i.name,i.customer_part,i.supplier_part,p.price,c.name  as currency,c.country as country,p.asOn from item i,price p , currency c where i.id=p.item_id and p.currency_id = c.id order by i.name",
				new ItemListExtractor());
	}

	private class ItemReportListExtractor implements ResultSetExtractor<List<Item>> {
		Map<Integer, Item> map;
		List<Item> itemList;
		public ItemReportListExtractor() {
			map = new LinkedHashMap<>();
			itemList = new ArrayList<>();
		}

		public List<Item> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Item item = new Item();
			while (rs.next()) {
				Integer itemId = rs.getInt("item_id");
				if(map.containsKey(itemId)){
					item = map.get(itemId);
				}else{
					item = new Item();
					item.setId(itemId);
					map.put(itemId, item);
					item.setId(rs.getInt("item_id"));
					item.setCustomerPart(rs.getString("customer_part"));
					item.setSupplierPart(rs.getString("supplier_part"));
					item.setName(rs.getString("name"));
				}
				Price price = new Price();
				price.setPrice(rs.getFloat("price"));
				price.setAsOn(rs.getDate("asOn"));
				Currency currency = new Currency();
				currency.setName(rs.getString("currency"));
				currency.setCountry(rs.getString("country"));
				price.setCurrency(currency);
				item.addPrice(price);
			}
			return new ArrayList<>(map.values());
		}

	}
}
