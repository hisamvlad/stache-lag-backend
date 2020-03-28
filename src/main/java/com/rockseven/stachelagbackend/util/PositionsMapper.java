package com.rockseven.stachelagbackend.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.rockseven.stachelagbackend.domain.Positions;

// class required for execution (mapping) SELECT query in RaceDataDaoImpl
// getPositionsForMoment(Date date)

public class PositionsMapper implements RowMapper<Positions>{

	@Override
	public Positions mapRow(ResultSet rs, int rowNum) throws SQLException {
		Positions position = new Positions();
		
		position.setLatitude(rs.getDouble("latitude"));
		position.setLongitude(rs.getDouble("longitude"));
		position.setAltitude(rs.getInt("altitude"));
		position.setTeamSerial(rs.getInt("serial"));
		position.setTeamName(rs.getString("name"));
		
		return position;
	}
	
}
