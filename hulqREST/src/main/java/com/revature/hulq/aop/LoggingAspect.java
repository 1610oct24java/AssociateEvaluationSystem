package com.revature.hulq.aop;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.hulq.util.Error;

/**
 * The Class AOP.
 */
@Component
@Aspect
public class LoggingAspect
{	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * Trace logging, surrounds the given point cut with error logging.
	 *
	 * @param pjp
	 *            the pjp
	 */
	@Around("everything()")
	public Object traceLogging(ProceedingJoinPoint pjp) {
		
		// Setup for grabbing method information
		MethodSignature sign = (MethodSignature) pjp.getSignature();
		Class[] paramTypes = sign.getParameterTypes();
		String[] paramNames = sign.getParameterNames();
		Class[] excepType = sign.getExceptionTypes();
		
		List<String> type = new ArrayList<>();
		int i = 0;
		for (Class c : paramTypes) {
			if (paramNames != null) {
				if (paramNames.length != 0) {
					type.add(c.getSimpleName() + " " + paramNames[i]);
				} else {
					type.add(c.getSimpleName());
				}
			}
			i++;
		}
		List<String> except = new ArrayList<>();
		for (Class c : excepType) {
			except.add(c.getSimpleName());
		}
		Object obj = new Object();
		
		// Surround proceed in try catch
		try {
			
			obj = pjp.proceed();
			
		} catch(Exception ex) {
			log.info("Exception", ex);
		} catch (Throwable e) {
			Error.error("\nin Class:\t"
					+ sign.getDeclaringTypeName()
					+ "\nin Method:\t"
					+ sign.getName()
					+ "\nParameters:\n"
					+ type.toString()
					+ "\nMethod exceptions:\n"
					+ except, e);
		}
		return obj;
		
	}
	
	/**
	 * Pointcut for everything.
	 */
	@Pointcut("execution(* *.hulq.*.*(..))")
	public void everything() {
		//This function is empty, as it is used for the pointcut feature for everything
	}
	
}