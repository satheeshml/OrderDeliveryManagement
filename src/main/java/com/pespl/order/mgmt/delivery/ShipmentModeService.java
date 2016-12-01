package com.pespl.order.mgmt.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentModeService {
	@Autowired
	ShipmentModeDAO dao;

	public void insert(ShipmentMode mode) {
		dao.insert(mode);
	}
	
	public List<ShipmentMode> list(){
		return dao.list();
	}

}
