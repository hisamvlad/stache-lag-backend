package com.rockseven.stachelagbackend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;
import com.rockseven.stachelagbackend.util.DateUtil;
import com.rockseven.stachelagbackend.util.GeoUtil;
import com.rockseven.stachelagbackend.util.PositionsMapper;

@Component
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
		StringBuilder csv = new StringBuilder("Date,Average Sightings\n");
		jdbcTemplate.query("SELECT DATE(sightedAt) AS sighted, COUNT(*)/2 AS total"
				+ "FROM sightings GROUP BY DATE(sightedAt)", 
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException  {
					while(rs.next()) {
					csv.append(rs.getString("sighted"));
	                csv.append(",");
	                int totalSightings = rs.getInt("total");
	                csv.append(totalSightings / getTotalTeams());
	                csv.append("\n");
						}
					}
				});
		return csv.toString();
		
		
		
	}
	
}
