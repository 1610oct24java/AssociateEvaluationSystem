
package com.revature.aes.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Logging.
 */
@Aspect
@RestController
public class Logging {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * Trace.
	 *
	 * @param msg
	 *            the msg
	 */
	public void trace(String msg) {

		log.trace(msg);
	}
	
	/**
	 * Warn.
	 *
	 * @param msg
	 *            the msg
	 */
	public void warn(String msg) {

		log.warn(msg);
	}

	/**
	 * Info.
	 *
	 * @param msg
	 *            the msg
	 */
	public void info(String msg) {

		log.info(msg);
	}

/**
	 * Debug.
	 *
	 * @param msg
	 *            the msg
	 */
	public void debug(String msg) {

		log.debug(msg);
	}
	/**
	 * Error.
	 *
	 * @param msg
	 *            the msg
	 */
	public void error(String msg) {

		log.error(msg);
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
	public static String errorMsg(String message, Throwable t) {
		
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
			System.err.println(entire);
		} else {
			entire = entire
					+ "\nRoot Cause:\t"
					+ t.getClass().getSimpleName()
					+ "\nMessage:\t"
					+ t.getMessage();
		}
		
		return entire;
		
	}
	
	public static String getDate(Date now) {
		
		String dateFormat = "dd/MM/yyyy", timeFormat = "HH:mm:ss";
		SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat),
				sdfTime = new SimpleDateFormat(timeFormat);
		String timeStamp = sdfDate.format(now) + " | " + sdfTime.format(now);
		return timeStamp;
	}
}
