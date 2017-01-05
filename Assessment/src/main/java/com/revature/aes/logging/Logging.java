
package com.revature.aes.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Logging.
 */
@RestController
public class Logging {
	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
	
}
