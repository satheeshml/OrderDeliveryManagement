package com.pespl.order.mgmt.delivery;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pespl.order.mgmt.common.Menu;

@Controller

public class ShipmentModeController {

	private static final Logger LOG = Logger.getLogger(ShipmentModeController.class);
	@Autowired
	ShipmentModeService service;

	@RequestMapping("/shipment")
	public ModelAndView insert(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView("shipment/shipmentMode");
		
		String mode = request.getParameter("mode");
		LOG.info("MODE >> "+mode);
		if (mode != null && !"".equals(mode)) {
			ShipmentMode shipmentMode = new ShipmentMode();
			shipmentMode.setMode(mode);
			service.insert(shipmentMode);
		}
		modelView.addObject("modeList", service.list());
		modelView.addObject("menuList", Arrays.asList(Menu.values()));
		return modelView;
	}
}