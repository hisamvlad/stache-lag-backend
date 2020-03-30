package com.rockseven.stachelagbackend.util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rockseven.stachelagbackend.dao.RaceDataDao;

// extract records from Db to a csv file

@Component
public class CsvMapperUtil {

	private JdbcTemplate jdbcTemplate;
	
	private RaceDataDao raceDataDao;
	
	public CsvMapperUtil(JdbcTemplate jdbcTemplate, RaceDataDao raceDataDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.raceDataDao = raceDataDao;
	}
	
	
	public void generateCsv() {
		 File csvOutputFile = new File("C:/temp/sightings.csv");
		   
	 // Takes in a String returned by getAverageSightingsPerDay, formats it and outputs it to the file as sightsinghs.csv
		
	}
}
