
package com.revature.aes.aop;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.logging.Logging;

/**
 * The Class AOP.
 */
@Component
@Aspect
public class LoggingAspect
{
	@Autowired
	Logging log;
	/**
	 * Trace logging, surrounds the given point cut with error logging.
	 *
	 * @param pjp
	 *            the pjp
	 */
	@Around("everything()")
	public void traceLogging(ProceedingJoinPoint pjp) {
		
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
		log.info("\nin Class:\t"
				+ sign.getDeclaringTypeName()
				+ "\nin Method:\t"
				+ sign.getName()
				+ "\nParameters:\n"
				+ type+" called.");
		// Surround proceed in try catch
		try {
			pjp.proceed();
		} catch (Throwable e) {
			log.error(Logging.errorMsg("\nin Class:\t"
					+ sign.getDeclaringTypeName()
					+ "\nin Method:\t"
					+ sign.getName()
					+ "\nParameters:\n"
					+ type
					+ "\nMethod exceptions:\n"
					+ except, e));

			for(StackTraceElement st : e.getStackTrace()){
				log.debug(st.getMethodName() + " at line " + st.getLineNumber());
			}
			
		}
		
	}
	
	/**
	 * Pointcut for everything.
	 */
	@Pointcut("execution(* *.aes.*.*(..))")
	public void everything() {
		
	}
	
}
