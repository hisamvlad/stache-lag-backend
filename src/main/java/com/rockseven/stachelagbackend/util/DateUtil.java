package com.rockseven.stachelagbackend.util;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class DateUtil {
	
	 public static Date addFifteenMinutes(Date date) {
	        Calendar calendar = getInstance();
	        calendar.setTime(date);
	        calendar.add(MINUTE, 15);
	        return new Date(calendar.getTimeInMillis());
	    }

	public   static Date addFourHours(Date date) {
	        Calendar calendar = getInstance();
	        calendar.setTime(date);
	        calendar.add(HOUR, 4);
	        return new Date(calendar.getTimeInMillis());
	    }
}
