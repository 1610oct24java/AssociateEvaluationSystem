
package com.revature.aes.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
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
	private String dashes = "\n==========================================================================================================================================================================";

	@Around("execution(* com.revature.aes..*(..))")
	public Object log(ProceedingJoinPoint pjp){
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		String methodClass = signature.getDeclaringTypeName().toString();
		String method = signature.getName().toString();
		Object result = null;

		log.info(dashes);

		log.info(methodClass + " ==> " + method);
		Object[] args = pjp.getArgs();
		for(int i = 0; i < args.length; i++){
			log.info("Argument #"+i+": "+args[i]);
		}

		log.info("Executing...");

		try{
			result = pjp.proceed();
		} catch(Exception e){
			log.error("\t" + e.getClass() + " " + e.getMessage());
			log.info(e.toString());

			for(StackTraceElement st : e.getStackTrace()){
				log.error("\t\t" + st.getMethodName());
			}
		} catch(Throwable e){
			log.error(e.toString());
		}

		log.info(methodClass + " ==> " + method + " - Exit\nReturning: " + result);

		log.info(dashes);
		return result;
	}

	@AfterThrowing(pointcut="execution(* com.revature.aes..*(..))", throwing="e")
	public void stackTraceLogging(Exception e){
		log.error(dashes);
		for(StackTraceElement st : e.getStackTrace()){
			log.error(st.getMethodName() + " at line " + st.getLineNumber());
		}
		log.error(dashes);
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

}
