package com.rockseven.stachelagbackend.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

// extract records from Db to a csv file

@Component
public class CsvMapperUtil {

	private JdbcTemplate jdbcTemplate;
	
	public CsvMapperUtil(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void produceCsvFromDb() {
		jdbcTemplate.queryForObject("SELECT DATE(sightedAt) AS sighted, COUNT(*)/2 AS total FROM"
				+ "sightings GROUP BY DATE(sightedAt)"
				+ "INTO OUTFILE 'C:/temp/sightings.csv'"
				+ "FIELDS ENCLOSED BY '\"' TERMINATED BY ';' ESCAPED BY '\"' "
						+ "LINES TERMINATED BY '\r\n'",
				String.class
				);
		
	
	}
}
