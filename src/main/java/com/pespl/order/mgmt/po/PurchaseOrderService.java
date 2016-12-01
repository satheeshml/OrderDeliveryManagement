package com.pespl.order.mgmt.po;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderDAO dao;

	public void save(PurchaseOrder purchaseOrder) {
		dao.save(purchaseOrder);
	}

	public void amend(PurchaseOrder purchaseOrder) {
		dao.amend(purchaseOrder);
	}

	public Boolean isExist(Long poNumber){
		PurchaseOrder po = dao.getByNumber(poNumber);
		return ( po!=null && po.getId()!=null);
	}
	
	public List<PurchaseOrder> list() {
		return dao.list();
	}

	public List<PurchaseOrder> pendingList(Integer customerId) {
		List<PurchaseOrder> poList =  dao.list(customerId);
		List<PurchaseOrder> pendingOrders = new ArrayList<>();
		for(PurchaseOrder po : poList){
			Collection<PurchaseRequest> pendingRequests = dao.getPendingPurchaseRequests(po.getId());
			if(pendingRequests!=null && !pendingRequests.isEmpty()){
				pendingOrders.add(po);
			}
		}
		return pendingOrders;
	}
	
	public PurchaseOrder get(Integer id){
		PurchaseOrder po =  dao.get(id);
		po.getPurchaseRequests().addAll(listPendingPurchaseRequests(id));
		return po;
	}

	public Collection<PurchaseRequest> listPendingPurchaseRequests(Integer poId) {
		Collection<PurchaseRequest> purchaseRequests = dao.getPendingPurchaseRequests(poId);
		for (PurchaseRequest pr : purchaseRequests) {
			for (PurchaseItem item : pr.getPurchaseItems()) {
				Integer deliveredQuantity = dao.getDeliveredQuantity(pr.getId(), item.getItem().getId());
				if (deliveredQuantity == null) {
					deliveredQuantity = 0;
				}
				int pending = item.getQuantity() - deliveredQuantity;
				item.setPendingQuantity(pending);				
			}
		}
		return purchaseRequests;
	}
	
	public List<PurchaseOrder> buildReport(){
		List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
		
		
		
		return purchaseOrderList;
	}
}
