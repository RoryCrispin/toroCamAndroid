/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 * 
 * Distributed under the Creative Commons 
 * Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * License, full conditions can be found here: 
 * http://creativecommons.org/licenses/by-sa/3.0/
 *   
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 *   
 *   Go crazy,
 *   Rozz xx 
 * 
 */
package com.rozzles.camera;

import java.util.concurrent.TimeUnit;

public class TimeParse {
	/*
	 * This class is used to convert the data used in the timelapse which is sent in 
	 * TODO Seconds/Milliseconds, I think I changed this and forgot to update the names!
	 * into a string with the format:
	 * W Days X Hours Y Min Z Sec 
	 * This shows the length of the timelapse.
	 */
	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException(
					"Duration must be greater than zero!");
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
		sb.append(" Min ");
		sb.append(seconds);
		sb.append(" Sec");

		return (sb.toString());
	}
}
