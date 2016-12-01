package com.pespl.order.mgmt.po;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pespl.order.mgmt.common.Menu;
import com.pespl.order.mgmt.customer.Customer;
import com.pespl.order.mgmt.customer.CustomerService;
import com.pespl.order.mgmt.delivery.ShipmentMode;
import com.pespl.order.mgmt.delivery.ShipmentModeService;
import com.pespl.order.mgmt.item.Item;
import com.pespl.order.mgmt.item.ItemService;

@Controller
public class PurchaseOrderController {

	private static final Logger LOG = Logger.getLogger(PurchaseOrderController.class);

	@Autowired
	private PurchaseOrderService service;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ShipmentModeService modeService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/purchaseOrder")
	public ModelAndView save(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView("po/po");
		String poNumberTxt = request.getParameter("poNumber");
		String customerTxt = request.getParameter("customer");
		LOG.info("poNumber ==>" + poNumberTxt);
		LOG.info("customer ==>" + customerTxt);
		if (!StringUtils.isEmpty(poNumberTxt) && !StringUtils.isEmpty(customerTxt)) {
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			Customer customer = new Customer();
			customer.setId(Integer.valueOf(customerTxt));
			purchaseOrder.setCustomer(customer);
			purchaseOrder.setNumber(Long.valueOf(poNumberTxt));
			
			if (service.isExist(purchaseOrder.getNumber())) {
				modelView.addObject("errorMsg", "Purchase Order Number already Exist.");
			} else {
				build(purchaseOrder, request);
				service.save(purchaseOrder);
				modelView.addObject("successMsg", "Purchase Order Created Successfully.");
			}
		}
		modelView.addObject("poList", service.list());
		modelView.addObject("itemList", itemService.list());
		modelView.addObject("modeList", modeService.list());
		modelView.addObject("customerList", customerService.list());
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}

	@RequestMapping("/amendPO")
	public ModelAndView amend(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView("po/amend");
		String id = request.getParameter("po_id");
		String lastRequestId = request.getParameter("requestLastId");
		if (!StringUtils.isEmpty(id) && StringUtils.isEmpty(lastRequestId)) {
			Integer poId = Integer.valueOf(id);
			PurchaseOrder po = service.get(poId);
			modelView.addObject("po", po);
		} else {
			modelView = new ModelAndView("po/po");
			Integer poId = Integer.valueOf(id);
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			purchaseOrder.setId(poId);
			build(purchaseOrder, request);
			service.amend(purchaseOrder);
			modelView.addObject("successMsg", "Purchase Order Amended Successfully.");
			modelView.addObject("poList", service.list());
			modelView.addObject("customerList", customerService.list());
		}
		modelView.addObject("itemList", itemService.list());
		modelView.addObject("modeList", modeService.list());
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}

	private PurchaseOrder build(PurchaseOrder purchaseOrder, HttpServletRequest request) throws Exception {
		Integer lastRequestId = Integer.valueOf(request.getParameter("requestLastId"));
		LOG.info("requestLastId ==>" + request.getParameter("requestLastId"));
		for (int i = 1; i <= lastRequestId; i++) {
			PurchaseRequest pr = new PurchaseRequest();
			ShipmentMode mode = new ShipmentMode();
			String modeTxt = request.getParameter("pr_" + i + "_mode");
			String piText = request.getParameter("pr_" + i + "_pi");
			String poDateText = request.getParameter("pr_" + i + "_poDate");
			if (!StringUtils.isEmpty(piText)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				pr.setProformaInvoiceDate(dateFormat.parse(piText));
			}
			if (!StringUtils.isEmpty(poDateText)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				pr.setPurchaseOrderDate(dateFormat.parse(poDateText));
			}
			LOG.info("pr_" + i + "_mode ==> " + modeTxt);
			String prItemCounterTxt = request.getParameter("pr_" + i + "_itemCounter");
			LOG.info("pr_" + i + "_itemCounter ==> " + prItemCounterTxt);
			if (StringUtils.isEmpty(modeTxt) || StringUtils.isEmpty(prItemCounterTxt)) {
				continue;
			}
			mode.setId(Integer.valueOf(modeTxt));
			pr.setMode(mode);
			pr.setDateCreated(new Date(System.currentTimeMillis()));
			Integer itemCounter = Integer.valueOf(prItemCounterTxt);
			for (int j = 1; j <= itemCounter; j++) {
				PurchaseItem prItem = new PurchaseItem();
				String itemText = request.getParameter("pr_" + i + "_prItem_" + j + "_item");
				String quantityText = request.getParameter("pr_" + i + "_prItem_" + j + "_quantity");

				LOG.info("pr_" + i + "_prItem_" + j + "_item ==> " + itemText);
				LOG.info("pr_" + i + "_prItem_" + j + "_quantity ==> " + quantityText);

				if (StringUtils.isEmpty(itemText) || StringUtils.isEmpty(quantityText)) {
					continue;
				}
				Item item = new Item();
				item.setId(Integer.valueOf(itemText));
				prItem.setItem(item);
				prItem.setQuantity(Integer.valueOf(quantityText));
				pr.addPurchaseItem(prItem);
			}
			purchaseOrder.addPurchaseRequests(pr);
		}
		return purchaseOrder;
	}
}
