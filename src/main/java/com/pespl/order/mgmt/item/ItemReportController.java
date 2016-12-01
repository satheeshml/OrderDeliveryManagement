package com.pespl.order.mgmt.item;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pespl.order.mgmt.common.Menu;

@Controller
public class ItemReportController {

	private static final Logger LOG = Logger.getLogger(ItemReportController.class);

	@Autowired
	private ItemService service;

	@RequestMapping("itemReport")
	public ModelAndView insert(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView("item/report");
		modelView.addObject("priceList", service.getPriceHistoryReport());
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}

}
