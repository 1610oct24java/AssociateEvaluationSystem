
package com.revature.aes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.revature.aes.logging.Logging;

/**
 * The Error class <br>
 * Primarily used for generating error reports for specified exceptions,<br>
 * but also provides a method to create a time stamp.
 */
public class Error {
	
	private Error(){}
	
	/**
	 * Gets the date.<br>
	 * Used in {@link #error} to create timestamp
	 *
	 * @param now
	 *            the now
	 * @return the date
	 */
	public static String getDate(Date now) {
		
		String dateFormat = "dd/MM/yyyy";
		String timeFormat = "HH:mm:ss";
		SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
		SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat);
		return sdfDate.format(now) + " | " + sdfTime.format(now);
	}
	
	/**
	 * Produces an error report, also sends errors to log4j2. Specifically:<br>
	 * <ul>
	 * <li>Borders</li>
	 * <li>Time</li>
	 * <li>User Message</li>
	 * <li>Cause</li>
	 * <li>Error message</li>
	 * </ul>
	 * Uses {@link #getDate(Date)}.
	 *
	 * @param message
	 *            the message from the call <br>
	 *            To make the most of this, set the call to look like this:<br>
	 *            {@code 
	 *            StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
	 *            Error.error("\nat Line:\t" + thing.getLineNumber()
	 *            + "\nin Method:\t" + thing.getMethodName()
	 *            + "\nin Class:\t" + thing.getClassName(), e);}
	 * @param t
	 *            the Throwable object passed in
	 */
	public static void error(String message, Throwable t) {
		
		Date now = new Date();
		Logging log = new Logging();
		
		String border = "\n==================================\n";
		String entire = border
				+ "Program ran into the following error:\n"
				+ "at Time:\t"
				+ getDate(now)
				+ message;
		
		if (t == null) {
			entire = entire + border;
			log.warn(entire);
		} else {
			entire = entire
					+ "\nRoot Cause:\t"
					+ t.getClass().getSimpleName()
					+ "\nMessage:\t"
					+ t.getMessage();
			log.warn(entire + "\nStackTrace:\n");
			log.warn(t.getMessage());
			log.warn(border);
		}
		
		log.warn(entire);
		
	}
}
