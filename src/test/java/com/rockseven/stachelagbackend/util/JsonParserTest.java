package com.rockseven.stachelagbackend.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rockseven.stachelagbackend.domain.Payload;

class JsonParserTest {

	@Autowired
	private static Payload payload;

//	@Autowired
//	private JsonParser jsonParser;
	
	// TODO: check it's worth working with original file or 
	// mock file is suficcient
	
	
	@BeforeAll
	public static void parsingFileForTest() {
		File file = new File("src/main/resources/json/positions.json");
		payload = JsonParser.parseJSONFile(file);
	}
	
	@Test
	void testRaceUrl() {
		assertEquals("arc2017", payload.getRaceUrl());
		
	}
	
	@Test
	void testTeams() {
		assertEquals("Mbolo", payload.getTeams().get(0).getName());
	}
	
	@Test
	void testPositions() {
		assertNotNull(payload.getTeams().get(0).getPositions().get(0).getId());
	}

}
