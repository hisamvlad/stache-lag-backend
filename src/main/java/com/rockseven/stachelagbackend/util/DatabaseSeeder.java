package com.rockseven.stachelagbackend.util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rockseven.stachelagbackend.StacheLagBackendApplication;
import com.rockseven.stachelagbackend.dao.RaceDataDao;
import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;


public class DatabaseSeeder implements CommandLineRunner{
	
	private JdbcTemplate jdbcTemplate;
	
	private RaceDataDao raceDataDao;
	
	private JsonParser jsonParser;
	
	public DatabaseSeeder(RaceDataDao raceDataDao, JdbcTemplate jdbcTemplate, JsonParser jsonParser) {
		this.raceDataDao = raceDataDao;
		this.jdbcTemplate = jdbcTemplate;
		this.jsonParser = jsonParser;
	}
	
	private static final Logger log = LoggerFactory.getLogger(StacheLagBackendApplication.class);


	@Override
	public void run(String... args) throws Exception {

	    log.info("Creating tables");

	    jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
	    
	    //Creating "teams" table
	    jdbcTemplate.execute("CREATE TABLE teams (\r\n" + 
	    		"	name VARCHAR(100),\r\n" + 
	    		"	marker INT,\r\n" + 
	    		"	serial INT\r\n" + 
	    		"	PRIMARY KEY (serial)\r\n" + 
	    		")" );
	    
	    // Creating "positions" table
	    jdbcTemplate.execute("CREATE TABLE positions (\r\n" + 
	    		"	id INT,\r\n" + 
	    		"	serial INT,\r\n" + 
	    		"	gpsAt TIMESTAMP,\r\n" + 
	    		"	txtAt TIMESTAMP,\r\n" + 
	    		"	latitude DOUBLE,\r\n" + 
	    		"	logitude DOUBLE,\r\n" + 
	    		"	altitude INT,\r\n" + 
	    		"	alert BOOLEAN,\r\n" + 
	    		"	type VARCHAR(10)\r\n" + 
	    		"	dtfKm DOUBLE,\r\n" + 
	    		"	dtfNm DOUBLE,\r\n" + 
	    		"	sogKmph DOUBLE,\r\n" + 
	    		"	sogKnots DOUBLE,\r\n" + 
	    		"	battery INT,\r\n" + 
	    		"	cog INT,\r\n" + 
	    		"	PRIMARY KEY(id), FOREIGN KEY (serial) REFERENCES teams(serial)");
	    
	    // Creating "sightings" table
	    jdbcTemplate.execute("CREATE TABLE sightings(\r\n" + 
	    		"	serial INT,\r\n" + 
	    		"	serial_spotted INT,\r\n" + 
	    		"	spottedAt TIMESTAMP,\r\n" + 
	    		"	FOREIGN KEY (serial) REFERENCES teams(serial),\r\n" + 
	    		"	FEREIGN KEY (serial_spotted) REEFRENCES teams(serial)");
		
	}
	//Parsing the file
			File jsonFile = new File("/json/positions.json");
			Payload payload = jsonParser.parseJSONFile(jsonFile);
	
	// Methods to populate DBs
			public void populateTeams(List<Teams> teams) {
				 jdbcTemplate.batchUpdate("INSERT INTO teams (name, marker, serial) VALUES (?, ?, ?)",
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								i = 0;
								for (Teams team : teams) {
								ps.setString(1, team.getName());
								ps.setInt(2, team.getMarker());
								ps.setInt(3, team.getSerial());
								ps.addBatch();
								i++;
								}
							}

							@Override
							public int getBatchSize() {
								return teams.size();
							}
				}
						);
				
			}
			
				public void populatePositions(List<Teams> teams) {
					jdbcTemplate.update("INSERT INTO positions(" + 
							"id, serial, gpsAt, txAt, latitude, longitude, altitude, "
							+ "alert, type, dtfKm,	dtfNm, sogKmph,	sogKnots, battery, cog )" + 
								"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
									new BatchPreparedStatementSetter() {
										public void setValues(PreparedStatement ps, int i) throws SQLException {
							
											for (Teams team : teams) {
												i = 0;
												int serial = team.getSerial();
												List<Positions> positions = team.getPositions();
								for(Positions position : positions) {
							ps.setInt(1, position.getId());
							ps.setInt(2, serial);
							ps.setTimestamp(3, position.getGpsAt());
							ps.setTimestamp(4, position.getTxAt());
							ps.setDouble(5, position.getLatitude());
							ps.setDouble(6, position.getLongitude());
							ps.setInt(7, position.getAltitude());
							ps.setBoolean(8,  position.isAlert());
							ps.setString(9,  position.getType());
							ps.setDouble(10, position.getDtfKm());
							ps.setDouble(11, position.getDtfNm());
							ps.setDouble(12, position.getSogKmph());
							ps.setDouble(13, position.getSogKnots());
							ps.setInt(14, position.getBattery());
							ps.setInt(15, position.getCog());
							ps.addBatch();
							i++;
							}
							}
						}
		
						@Override
						public int getBatchSize() {
							return teams.size();
				}
	}
			);
			}

			public void populateSightings(Date raceStart, Date raceFinish) {
				 Date date = raceStart;
			        while (date.before(raceFinish)) {
			            List<Positions> moment = raceDataDao.getPositionsForMoment(date);
			            GeoUtil.generateSightingData(moment, date);
			            date = DateUtil.addFourHours(date);
			        }
				
			}
			
			public void writeSightingsToDb(Date sampleDate, int teamSerial, List<Positions> seen) {
				jdbcTemplate.update("INSERT INTO sightings(serial, serial_spotted, TIMESTAMP ) VALUES(?, ?, ?)",
						new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						i = 0;
						for (Positions each : seen) {
								ps.setInt(1, teamSerial);
								ps.setInt(2, each.getTeamSerial());
								ps.setTimestamp(3, new Timestamp(sampleDate.getTime()));
								ps.addBatch();
								i++;
							}
					}
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return 0;
					
					}
				}	
					);
				}
			
		
}
