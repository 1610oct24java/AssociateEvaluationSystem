package com.revature.hulq.logging;

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

}