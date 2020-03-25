package com.rockseven.stachelagbackend.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rockseven.stachelagbackend.StacheLagBackendApplication;
import com.rockseven.stachelagbackend.dao.RaceDataDao;
import com.rockseven.stachelagbackend.domain.Payload;


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
		
}
