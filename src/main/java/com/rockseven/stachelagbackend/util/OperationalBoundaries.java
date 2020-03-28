package com.rockseven.stachelagbackend.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class OperationalBoundaries {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Date raceStart() {
		return raceBoundary("MIN");
	}

	
	public Date raceFinish() {
		return raceBoundary("MAX");
	}


	public Date raceBoundary(String stat) {
		return jdbcTemplate.queryForObject("SELECT " + stat.toUpperCase() + 
				"(gpsAt) AS boundary FROM positions", 
				Date.class);
	}
}
