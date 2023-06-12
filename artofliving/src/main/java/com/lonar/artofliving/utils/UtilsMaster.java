package com.lonar.artofliving.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UtilsMaster {
	
		public static Date getCurrentDateTime() {
			 Instant instant = Instant.now();
			 ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
			 try {
				 Calendar calendar = new GregorianCalendar();
				 calendar.set(jpTime.getYear(), jpTime.getMonthValue()-1, jpTime.getDayOfMonth(), jpTime.getHour(), jpTime.getMinute(), jpTime.getSecond());
				 return calendar.getTime();
			 } catch (Exception e1) {
				e1.printStackTrace();
			 }
			 return new Date();
		}

}
	

