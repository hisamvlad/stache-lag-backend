package com.rockseven.stachelagbackend.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;
import com.rockseven.stachelagbackend.util.DateUtil;
import com.rockseven.stachelagbackend.util.GeoUtil;
import com.rockseven.stachelagbackend.util.PositionsMapper;


public class RaceDataDaoImpl implements RaceDataDao {
	
	String raceUrl = "arc2017";

	private JdbcTemplate jdbcTemplate;
	
	private DateUtil dateUtil;
	
	private GeoUtil geoUtil;
	
	private PositionsMapper positionsMapper;
	
	public RaceDataDaoImpl(JdbcTemplate jdbcTemplate, DateUtil dateUtil, GeoUtil geoUtil, PositionsMapper positionsMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.dateUtil = dateUtil;
		this.geoUtil = geoUtil;
		this.positionsMapper = positionsMapper;
	}
		
	@Override
	public List<Positions> getPositionsForMoment(Date date) {
     
	Date dateInFifteenMinutes = DateUtil.addFifteenMinutes(date);
	 
		 String SQL = 
	                "SELECT id, teams.serial, name, latitude, longitude, altitude"
	                + "FROM positions, teams"
	                + "WHERE (gpsAt >= ?"
	                + "AND gpsAT <?)"
	                + "teams.serial = positions.serial"
	                + "ORDER BY dtfKm";
		 
	List<Positions> positions = jdbcTemplate.query(SQL, new PositionsMapper());	 
		
		return positions;
	
	}

	

	@Override
	public int getTotalTeams() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) AS total FROM teams", Integer.class);
	}

	@Override
	public String getAverageSightingsPerDay() {
		return jdbcTemplate.queryForObject("SELECT DATE(sightedAt) AS sighted, COUNT(*)/2 AS total"
				+ "FROM sightings GROUP BY DATE(sightedAt)", 
				String.class);
	}


	
}
