package com.pespl.order.mgmt.item;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

@Controller
public class ItemController {
 
	private static final Logger LOG = Logger.getLogger(ItemController.class);
	
	@Autowired
	private ItemService service;
	
	@Autowired
	private CustomerService cuService;
	
	@RequestMapping("item")
	public ModelAndView insert(HttpServletRequest request) throws Exception 
	{	
		ModelAndView modelView = new ModelAndView("item/item");
		String name = request.getParameter("name");
		String priceText = request.getParameter("price");
		LOG.info("name >> "+name);
		LOG.info("priceText >> "+priceText);
		String[] customerList = request.getParameterValues("customer");
		if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(priceText) && !StringUtils.isEmpty(customerList)){
			Item item = new Item();
			item.setName(name);
			String customerPart = request.getParameter("customerPart");
			String supplierPart = request.getParameter("supplierPart");
			
			if(!service.isExist(customerPart, supplierPart, name)){
				item.setCustomerPart(customerPart);
				item.setSupplierPart(supplierPart);
				
				LOG.info("Customer >> "+ customerList);
				for(int i=0;i<customerList.length;i++){
					Customer customer = new Customer();
					customer.setId(Integer.valueOf(customerList[i]));
					item.addCustomer(customer);
				}
				
				Date currentDate = new Date(System.currentTimeMillis());
				item.setCreated(currentDate);
				Price price = new Price();
				price.setAsOn(currentDate);
				price.setPrice(Float.valueOf(priceText));
				Currency currency = new  Currency();
				currency.setId(Integer.valueOf(request.getParameter("currency")));
				price.setCurrency(currency);
				item.setPrice(price);
				service.insert(item);
				modelView.addObject("successMsg", "Item added Successfully.");
			}else{
				modelView.addObject("errorMsg", "Item already exist. Can't be added twice.");				
			}
			
		}
		List<Item> itemList =  service.list();
		modelView.addObject("itemList",itemList);
		modelView.addObject("customerList",cuService.list());
		modelView.addObject("currencyList",service.listCurrency());
		modelView.addObject("action","INSERT");
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}
	
	@RequestMapping("changeItemPrice")
	public ModelAndView changePrice(HttpServletRequest req) throws Exception {
		ModelAndView modelView = new ModelAndView("item/item");
		Integer itemId = Integer.valueOf(req.getParameter("id"));
		String priceTxt = req.getParameter("price");
		if(!StringUtils.isEmpty(priceTxt)){
			Date currentDate = new Date(System.currentTimeMillis());
			Price price = new Price();
			price.setItemId(itemId);
			price.setAsOn(currentDate);
			Float newPrice = Float.valueOf(priceTxt);
			price.setPrice(newPrice);
			Currency currency = new  Currency();
			currency.setId(Integer.valueOf(req.getParameter("currency")));
			price.setCurrency(currency);
			service.changePrice(price);
			List<Item> itemList =  service.list();
			modelView.addObject("itemList",itemList);
			modelView.addObject("action","INSERT");
			modelView.addObject("successMsg", "Price Changed Successfully.");
		}else{
			Item item = service.get(itemId);
			modelView.addObject("action","CHANGE_PRICE");
			modelView.addObject("item",item);	
		}
		modelView.addObject("currencyList",service.listCurrency());
		modelView.addObject("customerList",cuService.list());
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}
	
}
