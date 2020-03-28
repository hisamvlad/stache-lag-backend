package com.rockseven.stachelagbackend.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rockseven.stachelagbackend.domain.Positions;
import com.rockseven.stachelagbackend.dao.*;

public class GeoUtil {
	
	@Autowired
	private static DatabaseSeeder databaseSeeder;
	
	
	
//	Calculate distance between two points in latitude and longitude taking
//    into account height difference. If you are not interested in height
//    difference pass 0.0. Uses Haversine method as its base.
//	This method was benevolently sponsored by StackOverflow
//https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude#16794680
	
	public static double distance(double lat1, double lat2, double lon1,
            double lon2, double el1, double el2) {

				final int R = 6371; // Radius of the earth
				
				double latDistance = Math.toRadians(lat2 - lat1);
				double lonDistance = Math.toRadians(lon2 - lon1);
				double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double distance = R * c * 1000; // convert to meters
				
				double height = el1 - el2;
				
				distance = Math.pow(distance, 2) + Math.pow(height, 2);
				
				return Math.sqrt(distance) / 1000.0; // Back to kilometers
	}

	/*
	Analyses the list of positions for vessels that can see each other and if they can, records
	data to the Db
  	According to W.H. Pick's book "Visibility At Sea", the excellent visibility allows 30 nautical miles
  	https://rmets.onlinelibrary.wiley.com/doi/pdf/10.1002/qj.49705824505
  	for vessels to see each other. 1 nautical mile = 1.85 km. Approx 55Km visibility.
	
	sampleDate Start time of the sample
	*/
	public static void generateSightingData(List<Positions> positions, Date sampleDate) {
        double visibilityKm = 55.0;
        /*
        No guarantee that positions will be free of duplicate positions from the same team.
        Data is ordered by distance to finish, so we can discard any serial we see a second time.
         */
        List<Integer> seenSerials = new LinkedList<>();

        for (Positions firstVessel : positions) {
            if(!seenSerials.contains(firstVessel.getTeamSerial())) {
                List<Positions> firstVesselSighting = new LinkedList<>();
                for (Positions secondVessel : positions) {
                    if(firstVessel.getTeamSerial() != secondVessel.getTeamSerial()) {
                        double distanceM = distance(firstVessel.getLatitude(),
                        		secondVessel.getLatitude(), 
                        		firstVessel.getLongitude(), 
                        		secondVessel.getLongitude(),
                        		firstVessel.getAltitude(), 
                        		secondVessel.getAltitude());
                        if(distanceM < visibilityKm) {
                            firstVesselSighting.add(secondVessel);
                        }
                    }
                }
                
                databaseSeeder.writeSightingsToDb(sampleDate, firstVessel.getTeamSerial(), firstVesselSighting);
                //System.out.println( "   * " + firstVessel.getTeamName() + " could see " +  firstVesselSighting.size() + " other participants");
                seenSerials.add(firstVessel.getTeamSerial());
            }
        }
    }
	
}
