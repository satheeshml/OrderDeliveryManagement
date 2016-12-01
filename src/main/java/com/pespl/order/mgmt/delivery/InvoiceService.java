package com.pespl.order.mgmt.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pespl.order.mgmt.po.PurchaseRequest;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceDAO dao;

	public void generate(Invoice invoice) {

		for (PurchaseRequest pr : invoice.getDeliveredRequests()) {
			PurchaseRequest prOld = dao.getDeliveredRequest(pr.getId());
			Boolean isDelivered = Boolean.TRUE;
			for (DeliveredItem item : pr.getDeliveredItems()) {
				DeliveredItem sameItem = null;
				for (DeliveredItem itemOld : prOld.getDeliveredItems()) {
					if (itemOld.getItem().getId() == item.getItem().getId()) {
						sameItem = itemOld;
						break;
					}
				}
				int pendingQuantity = item.getOrderedQuantity() - item.getQuantity();
				if (pendingQuantity!=0 && ( sameItem==null || pendingQuantity != sameItem.getQuantity() )) {
					isDelivered = Boolean.FALSE;
				}
			}
			if (isDelivered) {
				dao.markDelivered(pr.getId());
			}
		}
		dao.generate(invoice);

	}

	public List<Invoice> list() {
		return dao.list();
	}
	public Boolean isExist(String invoiceNumber){
		return dao.isExist(invoiceNumber);
	}
}
