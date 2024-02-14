package ru.patseev.monitoringservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;

import java.util.Arrays;

/**
 * Aspect for logging user actions and authentication/registration activities.
 */
@Component
@Aspect
public class LoggableAspect {

	/**
	 * Service for JWT operations.
	 */
	private final JwtService jwtService;

	/**
	 * Service for audit operations.
	 */
	private final AuditService auditService;

	/**
	 * Manager for handling actions associated with method names.
	 */
	private final ActionManager actionManager;

//	@Autowired
	public LoggableAspect(JwtService jwtService,
						  AuditService auditService,
						  ActionManager actionManager) {
		this.jwtService = jwtService;
		this.auditService = auditService;
		this.actionManager = actionManager;
	}

	/**
	 * Pointcut for methods annotated with @Loggable.
	 */
	@Pointcut("@annotation(ru.patseev.monitoringservice.aspect.annotation.Loggable)")
	public void annotatedByLoggable() {
	}

	/**
	 * Advice for logging user actions in MeterController and AuditController.
	 *
	 * @param proceedingJoinPoint The proceeding join point
	 * @return The result of the method execution
	 * @throws Throwable If an error occurs during method execution
	 */
	@Around("annotatedByLoggable() && execution(* ru.patseev.monitoringservice.in.controller.MeterController.*(..)) " +
			"|| " +
			"annotatedByLoggable() && execution(* ru.patseev.monitoringservice.in.controller.AuditController.*(..))")
	public Object loggingUserAction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		ActionEnum action = getAction(proceedingJoinPoint);

		int userId = Arrays
				.stream(proceedingJoinPoint.getArgs())
				.filter(o -> o instanceof String)
				.map(Object::toString)
				.filter(str -> str.length() > 40)
				.findFirst()
				.map(jwtService::extractPlayerId)
				.orElseThrow(() -> new IllegalStateException("Unable to extractor user ID from arguments."));

		System.out.println(userId);

		auditService.saveUserAction(action, userId);
		return result;
	}

	/**
	 * Advice for logging authentication or registration activities in UserController.
	 *
	 * @param proceedingJoinPoint The proceeding join point
	 * @return The result of the method execution
	 * @throws Throwable If an error occurs during method execution
	 */
	@Around("annotatedByLoggable() && execution(* ru.patseev.monitoringservice.in.controller.UserController.*(..)) ")
	public Object loggingAuthAndRegister(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		ResponseEntity<?> result = (ResponseEntity<?>) proceedingJoinPoint.proceed();
		ActionEnum action = getAction(proceedingJoinPoint);

		String jwtToken = result.getBody().toString();
		int userId = jwtService.extractPlayerId(jwtToken);

		auditService.saveUserAction(action, userId);
		return result;
	}

	/**
	 * Retrieves the action associated with the method being executed.
	 *
	 * @param proceedingJoinPoint The proceeding join point representing the method execution
	 * @return The ActionEnum associated with the method
	 */
	private ActionEnum getAction(ProceedingJoinPoint proceedingJoinPoint) {
		String methodName = proceedingJoinPoint.getSignature().getName();
		return actionManager.getActionByMethodName(methodName);
	}
}
