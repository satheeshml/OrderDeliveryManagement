package com.pespl.order.mgmt.po;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;
import com.pespl.order.mgmt.customer.Customer;
import com.pespl.order.mgmt.delivery.DeliveredItem;
import com.pespl.order.mgmt.delivery.ShipmentMode;
import com.pespl.order.mgmt.item.Item;

@Component
public class PurchaseOrderDAO extends BaseDAO {

	public synchronized void save(PurchaseOrder po) {
		Integer poId = this.template.queryForInt("select max(id) from purchase_order") + 1;
		Object[] params = new Object[3];
		params[0] = poId;
		params[1] = po.getNumber();
		params[2] = po.getCustomer().getId();
		this.template.update("insert into purchase_order(id,number,customer_id) values(?,?,?)", params);
		List<PurchaseRequest> purchaseRequests = po.getPurchaseRequests();
		Integer prId = this.template.queryForInt("select max(id) from purchase_request");
		Integer prItemId = this.template.queryForInt("select max(id) from purchase_item");
		for (PurchaseRequest request : purchaseRequests) {
			prId = prId + 1;
			params = new Object[7];
			params[0] = prId;
			params[1] = poId;
			params[2] = request.getMode().getId();
			params[3] = request.getProformaInvoiceDate();
			params[4] = new Date(System.currentTimeMillis());
			params[5] = "ORDERED";
			params[6] = request.getPurchaseOrderDate();
			this.template.update("insert into purchase_request(id,po_id,mode_id,performa_invoice_date,date_created,state,po_date) values(?,?,?,?,?,?,?)",
					params);
			List<PurchaseItem> purchaseItems = request.getPurchaseItems();
			for (PurchaseItem item : purchaseItems) {
				prItemId = prItemId + 1;
				params = new Object[4];
				params[0] = prItemId;
				params[1] = prId;
				params[2] = item.getItem().getId();
				params[3] = item.getQuantity();
				this.template.update("insert into purchase_item(id,pr_id,item_id,quantity) values(?,?,?,?)", params);

			}
		}
	}

	public List<PurchaseOrder> list() {
		return this.template.query(
				"select po.id as id,po.number as number,po.customer_id as customer_id,c.name as customer_name from purchase_order po, customer c where po.customer_id=c.id",
				new PurchaseOrderListExtractor());
	}

	public List<PurchaseOrder> list(Integer customerId) {
		return this.template.query("select po.id as id,po.number as number,po.customer_id as customer_id,c.name as customer_name from purchase_order po, customer c where po.customer_id=c.id and customer_id=?", new Object[] { customerId },
				new PurchaseOrderListExtractor());
	}
	
	public void amend(PurchaseOrder po){
		List<PurchaseRequest> purchaseRequests = po.getPurchaseRequests();
		Integer prId = this.template.queryForInt("select max(id) from purchase_request");
		Integer prItemId = this.template.queryForInt("select max(id) from purchase_item");
		for (PurchaseRequest request : purchaseRequests) {
			prId = prId + 1;
			Object[] params = new Object[7];
			params[0] = prId;
			params[1] = po.getId();
			params[2] = request.getMode().getId();
			params[3] = request.getProformaInvoiceDate();
			params[4] = new Date(System.currentTimeMillis());
			params[5] = "ORDERED";
			params[6] = request.getPurchaseOrderDate();
			this.template.update("insert into purchase_request(id,po_id,mode_id,performa_invoice_date,date_created,state,po_date) values(?,?,?,?,?,?,?)",
					params);
			List<PurchaseItem> purchaseItems = request.getPurchaseItems();
			for (PurchaseItem item : purchaseItems) {
				prItemId = prItemId + 1;
				params = new Object[4];
				params[0] = prItemId;
				params[1] = prId;
				params[2] = item.getItem().getId();
				params[3] = item.getQuantity();
				this.template.update("insert into purchase_item(id,pr_id,item_id,quantity) values(?,?,?,?)", params);

			}
		}
	}

	public class PurchaseOrderListExtractor implements RowMapper<PurchaseOrder> {
		@Override
		public PurchaseOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			PurchaseOrder purchaseOder = new PurchaseOrder();
			purchaseOder.setId(rs.getInt("id"));
			purchaseOder.setNumber(rs.getLong("number"));
			Customer customer = new Customer();
			customer.setId(rs.getInt("customer_id"));
			customer.setName(rs.getString("customer_name"));
			purchaseOder.setCustomer(customer);
			return purchaseOder;
		}
	}

	public PurchaseOrder get(Integer id) {
		return this.template.query("select po.id as id,po.number as number,po.customer_id as customer_id,c.name customer_name from purchase_order po, customer c where po.customer_id=c.id and po.id=?", new Object[] { id },
				new PurchaseOrderExtractor());
	}

	public PurchaseOrder getByNumber(Long number) {
		return this.template.query("select po.id as id,po.number as number,po.customer_id as customer_id,c.name customer_name from purchase_order po, customer c where po.customer_id=c.id and po.number=?", new Object[] { number },
				new PurchaseOrderExtractor());
	}
	
	private class PurchaseOrderExtractor implements ResultSetExtractor<PurchaseOrder> {

		public PurchaseOrder extractData(ResultSet rs) throws SQLException, DataAccessException {
			PurchaseOrder purchaseOder = new PurchaseOrder();
			if (rs.next()) {
				purchaseOder.setId(rs.getInt("id"));
				purchaseOder.setNumber(rs.getLong("number"));
				Customer customer = new Customer();
				customer.setId(rs.getInt("customer_id"));
				customer.setName(rs.getString("customer_name"));
				purchaseOder.setCustomer(customer);
			}
			return purchaseOder;
		}

	}

	public Collection<PurchaseRequest> getPendingPurchaseRequests(Integer poId) {
		return this.template.query(
				"select pr.id as id,pr.performa_invoice_date as performa_invoice_date,pr.po_date as po_date, pr.state,m.mode as mode,m.id as mode_id,pi.id as pi_id,pi.item_id,pi.quantity as quantity,i.name,i.customer_part,i.supplier_part from purchase_request pr,purchase_item pi,item i,shipment_mode m where pr.id=pi.pr_id  and pi.item_id=i.id and pr.mode_id =m.id and pr.po_id=? and pr.state!=\"DELIVERED\"",
				new Object[] { poId }, new PurchaseRequestExtractor(Boolean.FALSE));
	}

	public static class PurchaseRequestExtractor implements ResultSetExtractor<Collection<PurchaseRequest>> {
		private Map<Integer, PurchaseRequest> purchaseRequestMap = new LinkedHashMap<>();
		private Boolean isDeliveredRequest;

		public PurchaseRequestExtractor(Boolean isDeliveredRequest) {
			this.isDeliveredRequest = isDeliveredRequest;
		}

		public PurchaseRequest extractRequest(ResultSet rs) throws SQLException {
			Integer purchaseRequestId = rs.getInt("id");
			PurchaseRequest purchaseRequest = null;
			if (purchaseRequestMap.containsKey(purchaseRequestId)) {
				purchaseRequest = purchaseRequestMap.get(purchaseRequestId);
			} else {
				purchaseRequest = new PurchaseRequest();
				ShipmentMode mode = new ShipmentMode();
				mode.setId(rs.getInt("mode_id"));
				mode.setMode(rs.getString("mode"));
				purchaseRequest.setId(purchaseRequestId);
				purchaseRequest.setMode(mode);
				purchaseRequest.setProformaInvoiceDate(rs.getDate("performa_invoice_date"));
				purchaseRequest.setPurchaseOrderDate(rs.getDate("po_date"));
				purchaseRequestMap.put(purchaseRequestId, purchaseRequest);
			}
			Item item = new Item();
			item.setId(rs.getInt("item_id"));
			Integer quantity = rs.getInt("quantity");
			if (isDeliveredRequest) {
				DeliveredItem drItem = new DeliveredItem();
				drItem.setItem(item);
				drItem.setQuantity(quantity);
				purchaseRequest.addDeliveredItem(drItem);
			} else {
				PurchaseItem prItem = new PurchaseItem();
				prItem.setId(rs.getInt("pi_id"));
				item.setCustomerPart(rs.getString("customer_part"));
				item.setSupplierPart(rs.getString("supplier_part"));
				item.setName(rs.getString("name"));
				prItem.setItem(item);
				prItem.setQuantity(quantity);
				purchaseRequest.addPurchaseItem(prItem);

			}
			return purchaseRequest;
		}

		@Override
		public Collection<PurchaseRequest> extractData(ResultSet rs) throws SQLException, DataAccessException {
			while (rs != null && rs.next()) {
				extractRequest(rs);
			}
			return purchaseRequestMap.values();
		}
	}

	public Integer getDeliveredQuantity(int prId, int itemId) {
		Integer delivered = 0;
		try {
			delivered = this.template.queryForInt(
					"select sum(quantity) as totalDelivered from delivered_item where pr_id=? and item_id=? group by item_id",
					new Object[] { prId, itemId });
		} catch (Exception e) {

		}
		return delivered;
	}
	
}
