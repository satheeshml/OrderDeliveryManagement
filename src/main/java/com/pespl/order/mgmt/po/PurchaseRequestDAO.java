package com.pespl.order.mgmt.po;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;
import com.pespl.order.mgmt.delivery.DeliveredItem;
import com.pespl.order.mgmt.item.Item;

@Component
public class PurchaseRequestDAO extends BaseDAO{

	public PurchaseRequest getPurchaseRequest(Integer prId){
	
		return this.template.query("", new PurchaseRequestExtractor(Boolean.TRUE,null));
		
	}
	
	public static class PurchaseRequestExtractor implements ResultSetExtractor<PurchaseRequest>{
		PurchaseRequest purchaseRequest;
		public PurchaseRequestExtractor(Boolean isDeliveredRequest,PurchaseRequest purchaseRequest) 
		{	
			if(purchaseRequest!=null){
					this.purchaseRequest = purchaseRequest;
			}else{
				this.purchaseRequest = new PurchaseRequest();
			}
			
		}
		@Override
		public PurchaseRequest extractData(ResultSet rs) throws SQLException, DataAccessException {
			while(rs.next()){
				purchaseRequest.setId(rs.getInt("id"));
				DeliveredItem deliveredItem = new DeliveredItem();
				Item item = new Item();
				item.setId(rs.getInt("item_id"));
				deliveredItem.setItem(item);
				deliveredItem.setQuantity(rs.getInt("quantity"));
				deliveredItem.setOrderedQuantity(rs.getInt("ordered_quantity"));
				purchaseRequest.addDeliveredItem(deliveredItem);	
			}
			return purchaseRequest;
		}
		
	}
	
	
	
}
