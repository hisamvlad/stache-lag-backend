package com.rockseven.stachelagbackend.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;

@TestInstance(Lifecycle.PER_CLASS)
class DatabaseSeederTest {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JsonParser jsonParser;
	
	@Autowired
	private DatabaseSeeder databaseSeeder;
	
	@Autowired
	private OperationalBoundaries operationalBoundaries;
	
//	public DatabaseSeederTest(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
	@BeforeAll
	void populateDb() {
		// jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		//new EmbeddedDatabaseBuilder().addScript("classpath:jdbc/schema.sql").build();
		File jsonFile = new File("json/positions.json");
		Payload payload = jsonParser.parseJSONFile(jsonFile);
		databaseSeeder.populatePositions(payload.getTeams());
		databaseSeeder.populateTeams(payload.getTeams());
		databaseSeeder.populateSightings(operationalBoundaries.raceStart(), operationalBoundaries.raceFinish());
	}
	
	@Test
	void testDbConnection() {
		assertNotNull(jdbcTemplate.getDataSource());
	}

	@Test
	void testTeamsTableNotEmpty() {
		assertNotNull(jdbcTemplate.queryForObject("SELECT COUNT(*) AS total FROM teams", Integer.class));
	}
	
	@Test
	void testPositionssTableNotEmpty() {
		assertNotNull(jdbcTemplate.queryForObject("SELECT COUNT(*) AS total FROM positions", Integer.class));
	}
	
	@Test
	void testSighingsTableCreation() {
		assertNotNull(jdbcTemplate.queryForObject("SELECT COUNT(*) AS total FROM sightings", Integer.class));
	}

	
}
