package com.rockseven.stachelagbackend.dao;

import java.util.Date;
import java.util.List;

import com.rockseven.stachelagbackend.domain.Payload;
import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.domain.Teams;

public interface RaceDataDao {
           
    List<Positions> getPositionsForMoment(Date date);     
    
    int getTotalTeams();
    
    String getAverageSightingsPerDay();
}
