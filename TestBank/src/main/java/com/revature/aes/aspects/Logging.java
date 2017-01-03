/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Logging
{
	private static Logger log = Logger.getRootLogger();
	@Pointcut("execution(* com.revature.aes..*(..))")
	public void anyMethod(){
		// This method is a hook to be used on any method (usefull for logging.)
	}
	
	@Around("anyMethod()")
	public Object log(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		String methodClass = signature.getDeclaringTypeName().toString();
		String method = signature.getName().toString();
		Object result = null;
		
		log.trace(methodClass + " ==> " + method);
		Object[] args = pjp.getArgs();
		for(int i = 0; i < args.length; i++){
			log.trace("Argumetnt #"+i+": "+args[i]);
		}
		
		log.trace("Executing...");
		try {
			result = pjp.proceed();
			log.trace(methodClass + " ==> " + method + " - Exit\nReturning: " + result);
		} catch (Throwable e) {
			log.warn("error in pjp" + e.getStackTrace());
		}

		return result;
	}
	
	@AfterThrowing(pointcut="anyMethod()", throwing="e")
	public void stackTraceLogging(Exception e){
		for(StackTraceElement st : e.getStackTrace()){
			log.error(st.getMethodName() + " at line " + st.getLineNumber());
		}
	}
}
