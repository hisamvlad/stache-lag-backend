package com.rockseven.stachelagbackend.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Positions {
	
	 	private boolean alert;
	    private int altitude;
	    private String type;
	    private double dtfKm;    //Distance to finish
	    private double dtfNm;
	    private int id;
	    private Timestamp gpsAt;
	    private int battery;
	    private int cog;
	    private Timestamp txAt;
	    private double latitude;
	    private double longitude;
	    private long gpsAtMillis;
	    private double sogKmph; //(Wind) Speed Over Ground
	    private double sogKnots;

	    //Not used in parsing
	    private int teamSerial;
	    private String teamName;

	    public Positions() {}

	  	    
	    public String toString() {
	        String string = "";
	        if(teamName != null) {
	            string += teamName + ": ";
	        } else if(teamSerial != 0) {
	            string += teamSerial;
	        }
	        return string + "(" + longitude + "," + latitude + "), " + altitude;
	    }
}
