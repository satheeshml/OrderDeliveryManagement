package com.pespl.order.mgmt.delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pespl.order.mgmt.BaseDAO;

@Component
public class ShipmentModeDAO extends BaseDAO {

	public void insert(final ShipmentMode mode) {
		this.template.update("insert into shipment_mode(mode) values(?)", new Object[] { mode.getMode() });
	}

	public List<ShipmentMode> list() {
		return this.template.query("select * from shipment_mode", new ShipmentModeExtractor());
	}

	private class ShipmentModeExtractor implements RowMapper<ShipmentMode> {
		@Override
		public ShipmentMode mapRow(ResultSet rs, int rowNum) throws SQLException {
			ShipmentMode mode = new ShipmentMode();
			mode.setMode(rs.getString("mode"));
			mode.setId(rs.getInt("id"));
			return mode;
		}
	}
}
