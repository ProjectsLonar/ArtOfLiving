package com.lonar.artofliving.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

		
		public static Date convertDate(Date date) {
			Instant instant = date.toInstant();
			ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
			
			try {
			Calendar calendar = new GregorianCalendar();
			calendar.set(jpTime.getYear(), jpTime.getMonthValue()-1, jpTime.getDayOfMonth(), jpTime.getHour()+5, jpTime.getMinute()+30, jpTime.getSecond());
			
			return calendar.getTime();
			} catch (Exception e1) {
			e1.printStackTrace();
			}
			return new Date();
			}
		
		
		public static String convertDateFormat(String strDate,String strFormatter)
		{
			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
			Date date;
			String newDate;
			if(strDate!=null)
			{
			try {
				date = (Date)formatter.parse(strDate);
				newDate=new SimpleDateFormat(strFormatter).format(date);
				return newDate;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			return strDate;
			}
		
		
		public static String convertCustomDateFormat(String strDate,String parsDateFormate,String strFormatter)
		{
			DateFormat formatter = new SimpleDateFormat(parsDateFormate);
			Date date;
			String newDate;
			if(strDate!=null)
			{
			try {
				date = (Date)formatter.parse(strDate);
				newDate=new SimpleDateFormat(strFormatter).format(date);
				return newDate;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return strDate;
			}}
			return strDate;
			}
		
		
		
		
		
}
	

