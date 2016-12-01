package com.pespl.order.mgmt.delivery;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pespl.order.mgmt.common.Menu;
import com.pespl.order.mgmt.customer.CustomerService;
import com.pespl.order.mgmt.item.Item;
import com.pespl.order.mgmt.po.PurchaseItem;
import com.pespl.order.mgmt.po.PurchaseOrder;
import com.pespl.order.mgmt.po.PurchaseOrderService;
import com.pespl.order.mgmt.po.PurchaseRequest;

@Controller
public class InvoiceController {

	private static final Logger LOG = Logger.getLogger(InvoiceController.class);

	@Autowired
	private InvoiceService service;

	@Autowired
	private PurchaseOrderService poService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/invoice")
	public ModelAndView generate(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView("invoice/invoice");
		modelView.addObject("poList", poService.list());
		modelView.addObject("customerList", customerService.list());
		Invoice invoice = new Invoice();
		String invoiceNumber = request.getParameter("invoice");
		LOG.info("invoice ==> " + invoiceNumber);
		Map<String, PurchaseRequest> map = new HashMap<>();
		if (!StringUtils.isEmpty(invoiceNumber)) {
			invoice.setNumber(invoiceNumber);
			if (service.isExist(invoiceNumber)) {
				modelView.addObject("errorMsg", "Invoice Number already Exist.");
			} else {
				Enumeration<String> paramNames = request.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String paramName = paramNames.nextElement();
					if (paramName.startsWith("quantity_")) {
						String quantity = request.getParameter(paramName);
						String orderedQuantity = request.getParameter("ordered_" + paramName);
						if (StringUtils.isEmpty(quantity)) {
							continue;
						}
						String[] purchaseRequest = paramName.split("_");
						String prId = purchaseRequest[1];
						String piId = purchaseRequest[2];
						String itemId = purchaseRequest[3];
						PurchaseRequest pr = null;
						DeliveredItem deliveredItem = new DeliveredItem();
						if (map.containsKey(prId)) {
							pr = map.get(prId);
						} else {
							pr = new PurchaseRequest();
							map.put(prId, pr);
						}

						pr.setId(Integer.valueOf(prId));
						Item item = new Item();
						item.setId(Integer.valueOf(itemId));
						PurchaseItem pi = new PurchaseItem();
						pi.setId(Integer.valueOf(piId));
						pi.setItem(item);
						pr.addPurchaseItem(pi);
						pr.addDeliveredItem(deliveredItem);
						deliveredItem.setPurchaseRequest(pr);
						deliveredItem.setItem(item);
						deliveredItem.setQuantity(Integer.valueOf(quantity));
						deliveredItem.setOrderedQuantity(Integer.valueOf(orderedQuantity));
						invoice.addDeliveredItem(deliveredItem);
						invoice.addDeliveredRequest(pr);
					}
				}
				String eta = request.getParameter("eta");
				String date = request.getParameter("invoice_date");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				invoice.setEta(dateFormat.parse(eta));
				invoice.setDate(dateFormat.parse(date));
				invoice.setDateCreated(new Date(System.currentTimeMillis()));
				service.generate(invoice);
				modelView.addObject("successMsg", "Invoice generated successfully.");
			}

		}
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}

	@RequestMapping("/poByCustomer")
	public ModelAndView listByCustomer(@RequestParam("customerId") String customerId) throws Exception {
		ModelAndView modelView = new ModelAndView("invoice/_po");
		LOG.info("CustomerId ==> " + customerId);
		List<PurchaseOrder> poList = poService.pendingList(Integer.valueOf(customerId));
		modelView.addObject("poList", poList);
		return modelView;
	}

	@RequestMapping("/prByPo")
	public ModelAndView fetchPendingPurchaseItems(HttpServletResponse response, @RequestParam("poId") String purchaseId)
			throws Exception {
		ModelAndView modelView = new ModelAndView("invoice/_pr");
		LOG.info("PurchaseId ==> " + purchaseId);
		Collection<PurchaseRequest> prList = poService.listPendingPurchaseRequests(Integer.valueOf(purchaseId));
		modelView.addObject("prList", prList);
		return modelView;
	}
}
