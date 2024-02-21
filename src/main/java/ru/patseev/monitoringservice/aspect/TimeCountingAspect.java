package ru.patseev.monitoringservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * Aspect for measuring the execution time of specific methods.
 */
@Component
@Aspect
public class TimeCountingAspect {

	/**
	 * Advice for measuring the execution time of the authUser method in UserServiceImpl.
	 *
	 * @param joinPoint The proceeding join point
	 * @return The result of the method execution
	 * @throws Throwable If an error occurs during method execution
	 */
	@Around("execution(* ru.patseev.monitoringservice.service.impl.UserServiceImpl.authUser(..))")
	public Object authorizationTimeCounting(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();

		System.out.printf("Execution of method %s finished. Execution time is %d ms\n",
				joinPoint.getSignature(), (endTime - startTime));

		return result;
	}
}
