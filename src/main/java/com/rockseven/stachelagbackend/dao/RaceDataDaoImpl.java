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


public class RaceDataDaoImpl implements RaceDataDao {
	
	String raceUrl = "arc2017";

	
	private JdbcTemplate jdbcTemplate;
	
	
	private DateUtil dateUtil;
	
	
	private GeoUtil geoUtil;
	
	public RaceDataDaoImpl(JdbcTemplate jdbcTemplate, DateUtil dateUtil, GeoUtil geoUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.dateUtil = dateUtil;
		this.geoUtil = geoUtil;
	}
	
	// TODO: make it a service implementing command runner
	// or under command runner itself
	

	@Override
	public Date raceStart() {
		return raceBoundary("MIN");
	}

	@Override
	public Date raceFinish() {
		return raceBoundary("MAX");
	}

	@Override
	public Date raceBoundary(String stat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateSightings(Date raceStart, Date raceFinish) {
		 Date date = raceStart;
	        while (date.before(raceFinish)) {
	            List<Positions> moment = getPositionsForMoment(date);
	            GeoUtil.generateSightingData(moment, date);
	            date = DateUtil.addFourHours(date);
	        }
		
	}

	@Override
	public List<Positions> getPositionsForMoment(Date date) {
     List<Positions> positions = new LinkedList<>();
   
     
	 Date dateInFifteenMinutes = DateUtil.addFifteenMinutes(date);
	 
		return jdbcTemplate.query(
	                "SELECT id, teams.serial, name, latitude, longitude, altitude"
	                + "FROM positions, teams"
	                + "WHERE (gpsAt >= ?"
	                + "AND gpsAT <?)"
	                + "teams.serial = positions.serial"
	                + "ORDER BY dtfKm",
	                (rs, rowNum) ->
	                        new positions (
	                                rs.getDouble("latitude"),
	                                rs.getDouble("longitude"),
	                                rs.getInt("altitude"),
	                                rs.getInt("serial"),
	                                rs.getString("name")
	                        )
	        
	                        );
	}

	@Override
	public void populateTeams(List<Teams> teams) {
		jdbcTemplate.update("INSERT INTO teams (name, marker, serial) VALUES (?, ?, ?)",
				((Teams) teams).getName(), 
				((Teams) teams).getMarker(), 
				((Teams) teams).getSerial());
		
	}

	@Override
	public void populatePositions(List<Positions> positions) {
		jdbcTemplate.update("INSERT INTO positions(\r\n" + 
				"id, serial, gpsAt, txtAt, latitude,	longitude, altitude, alert, type, dtfKm,	dtfNm, sogKmph,	sogKnots, battery, cog ) \r\n" + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				((Positions) positions).getId(), 
				((Teams) positions).getSerial(),
				((Positions) positions).getGpsAt(),
				((Positions) positions).getTxAt(),
				((Positions) positions).getLatitude(),
				((Positions) positions).getLongitude(),
				((Positions) positions).getAltitude(),
				((Positions) positions).isAlert(),
				((Positions) positions).getType(),
				((Positions) positions).getDtfKm(),
				((Positions) positions).getSogKmph(),
				((Positions) positions).getSogKnots(),
				((Positions) positions).getBattery(),
				((Positions) positions).getCog()
				);
		
	}

	@Override
	public void writeSightingsToDb(Date sampleDate, int teamSerial, List<Positions> seen) {
		jdbcTemplate.update("INSERT INTO sightings(serial, serial_spotted, TIMESTAMP ) VALUES(?, ?, ?)",
				teamSerial, seen, sampleDate); 
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
