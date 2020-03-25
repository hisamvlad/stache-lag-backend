package com.rockseven.stachelagbackend.dao;

import java.util.Date;
import java.util.List;

import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;

public interface RaceDataDao {
		
		
	// set the boundaries of report
    Date raceStart();
	
    Date raceFinish();
	   
    Date raceBoundary(String stat);
    
    void populateSightings(Date raceStart, Date raceFinish);
        
    List<Positions> getPositionsForMoment(Date date);
    
    //TODO: Check if these methods will work from data.sql
    void populateTeams(List<Teams> teams);
    void populatePositions(List<Positions> positions);
    void writeSightingsToDb(Date sampleDate, int teamSerial, List<Positions> seen);
    
    int getTotalTeams();
    
    String getAverageSightingsPerDay();
}
