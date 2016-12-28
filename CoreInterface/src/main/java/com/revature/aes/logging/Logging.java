package com.revature.aes.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
	private static Logger log = Logger.getRootLogger();
	private String dashes = "\n==========================================================================================================================================================================";
	
	@Around("execution(* com.revature.aes..*(..))")
	public Object log(ProceedingJoinPoint pjp){
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		String methodClass = signature.getDeclaringTypeName().toString();
		String method = signature.getName().toString();
		Object result = null;
		
		log.trace(dashes);
		
		log.trace(methodClass + " ==> " + method);
		Object[] args = pjp.getArgs();
		for(int i = 0; i < args.length; i++){
			log.trace("Argumetnt #"+i+": "+args[i]);
		}
		
		log.trace("Executing...");
		
		try{
			result = pjp.proceed();
		} catch(Throwable e){
			log.error("\t" + e.getClass() + " " + e.getMessage());
			
			for(StackTraceElement st : e.getStackTrace()){
				log.error("\t\t" + st.getMethodName());
			}
		}
		
		log.trace(methodClass + " ==> " + method + " - Exit\nReturning: " + result);

		log.trace(dashes);
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
}