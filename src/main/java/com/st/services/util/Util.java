package com.st.services.util;

import java.util.Date;

public class Util {
    
    public static boolean isEmpty(String str) {
        if(isNull(str) || str.trim().length() == 0) {
            return true;
        }
        return false;
    }
    
    public static boolean isNull(String str) {
        if(str == null) {
            return true;
        }
        return false;
    }
    
    public static Date getUtilDateFromSqlDate(java.sql.Date sDate) {
        if(sDate == null) {
            return null;
        }
        
        return new Date(sDate.getTime());
    }
    

    
    public static long getDateInMinutes(Date uDate) {
    	return (uDate.getTime())/(1000 * 60);
    }
    
	public static String currentTimestampStringInSeconds() {
		Date date = new Date();
		return Long.toString((date.getTime()) / 1000);
	}

	public static long currentTimestampInMinutes() {
		Date date = new Date();
		return (date.getTime()) / (1000 * 60);
	}    

}
