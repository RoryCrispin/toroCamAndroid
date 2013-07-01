package com.rozzles.cam;

import java.util.concurrent.TimeUnit;

public class TimeParse {
	   public static String getDurationBreakdown(long millis)
	    {
	        if(millis < 0)
	        {
	            throw new IllegalArgumentException("Duration must be greater than zero!");
	        }

	        long days = TimeUnit.SECONDS.toDays(millis);
	        millis -= TimeUnit.DAYS.toSeconds(days);
	        long hours = TimeUnit.SECONDS.toHours(millis);
	        millis -= TimeUnit.HOURS.toSeconds(hours);
	        long minutes = TimeUnit.SECONDS.toMinutes(millis);
	        millis -= TimeUnit.MINUTES.toSeconds(minutes);
	        long seconds = TimeUnit.SECONDS.toSeconds(millis);

	        StringBuilder sb = new StringBuilder(64);
	        sb.append(days);
	        sb.append(" Days ");
	        sb.append(hours);
	        sb.append(" Hours ");
	        sb.append(minutes);
	        sb.append(" Minutes ");
	        sb.append(seconds);
	        sb.append(" Seconds");

	        return(sb.toString());
	    }
}
