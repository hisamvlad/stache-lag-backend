package com.rockseven.stachelagbackend.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class DateUtilTest {
	
	/*
	 * Test that original date is preceding
	 * original date + 4 hours (addition method works correctly)
	 * */

	@Test
	public void testDateAndTimeComparison() {
		Date date = new Date(); 
		Date testDate = DateUtil.addFourHours(date);
		assertThat(testDate.after(date))
			.as("Four hours later comes after current time")
			.isTrue();
		
	}

}
