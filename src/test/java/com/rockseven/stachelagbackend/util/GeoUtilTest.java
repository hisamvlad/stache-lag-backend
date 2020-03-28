package com.rockseven.stachelagbackend.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeoUtilTest {

	@Test
    public void testFarehamToPortsmouthDistance() {
        assertThat(GeoUtil.distance(50.8548, 50.8198,-1.1866, -1.0880,0.0,0.0))
                .as("Checks  that distance between Rock Seven Fareham Office and London"
                		+ " is within the right range")
                .isBetween(7.0, 8.0);
    }

    @Test
    public void testSightingsDataGeneration() {
//        connectToDatabase();    //TODO: For a good unit test, this should be a separate database connection
//        Calendar calendar = getInstance();
//        //calendar.set(2017, NOVEMBER, 19, 20, 0, 0);
//        calendar.set(2017, NOVEMBER, 22, 16, 0, 0);
//        Date date = new Date(calendar.getTimeInMillis());
//        List<Positions> positions = getPositionsForMoment(date);
//        //Commented out, as will alter database
//        //DistanceHelper.generateSightingData(positions, date);
    }

}
