package com.pespl.order.mgmt.delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;
import com.pespl.order.mgmt.po.PurchaseRequest;
import com.pespl.order.mgmt.po.PurchaseRequestDAO;

@Component
public class InvoiceDAO extends BaseDAO{
	
	public synchronized void generate(Invoice invoice)
	{
		Integer invoiceId = this.template.queryForInt("select max(id) from invoice") +1;
		Object[] params = new Object[5];
		params[0] = invoiceId;
		params[1] = invoice.getNumber();
		params[2] = invoice.getEta();
		params[3] = invoice.getDateCreated();
		params[4] = invoice.getDate();
		this.template.update("insert into invoice(id,number,eta,date_created,date) values(?,?,?,?,?)",params);
		
		for(DeliveredItem item : invoice.getDeliveredItems())
		{
			Object[] item_params = new Object[5];
			item_params[0] = item.getPurchaseRequest().getId();
			item_params[1] = item.getItem().getId();
			item_params[2] = item.getQuantity();
			item_params[3] = invoiceId;
			item_params[4] = item.getOrderedQuantity();
			this.template.update("insert into delivered_item(pr_id,item_id,quantity,invoice_id,ordered_quantity) values(?,?,?,?,?)",item_params);
		}
	}
	
	
	public Boolean isExist(String invoiceNumber){
		List<Invoice> invoiceList =  this.template.query("select * from invoice where number=?", new Object[]{invoiceNumber} ,new InvoiceExtractor());
		return (invoiceList!=null && !invoiceList.isEmpty());
	}
	
	public void markDelivered(Integer prId){
		this.template.update("update purchase_request set state=\"DELIVERED\" where id=?",new Object[]{prId});
	}
	public PurchaseRequest getDeliveredRequest(Integer prId){
		return this.template.query("select pr.id as id,di.item_id as item_id,sum(di.quantity) as quantity,di.ordered_quantity from purchase_request pr,delivered_item di where pr.id=di.pr_id and pr_id=? group by di.item_id",new Object[]{prId}, new PurchaseRequestDAO.PurchaseRequestExtractor(Boolean.TRUE, null));
	}

	public List<Invoice> list(){
		return this.template.query("select * from invoice", new InvoiceExtractor());
	}
	
	private class InvoiceExtractor implements RowMapper<Invoice> {
		@Override
		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Invoice invoice = new Invoice();
			invoice.setId(rs.getInt("id"));
			invoice.setDateCreated(rs.getDate("date_created"));
			invoice.setDateUpdated(rs.getDate("date_updated"));
			invoice.setEta(rs.getDate("eta"));
			invoice.setDate(rs.getDate("date"));
			return invoice;
		}
	}
}
