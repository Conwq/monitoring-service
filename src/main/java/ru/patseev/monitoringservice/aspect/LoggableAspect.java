package ru.patseev.monitoringservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;

import java.util.Arrays;

/**
 * Aspect for logging user actions and authentication/registration activities.
 */
@Aspect
public class LoggableAspect {

	/**
	 * Service for JWT operations.
	 */
	private final JwtService jwtService = MonitoringApplicationContext.getContext().getJwtService();

	/**
	 * Service for audit operations.
	 */
	private final AuditService auditService = MonitoringApplicationContext.getContext().getAuditService();

	/**
	 * Manager for handling actions associated with method names.
	 */
	private final ActionManager actionManager = new ActionManager();

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
	@Around("annotatedByLoggable() && execution(* ru.patseev.monitoringservice.controller.MeterController.*(..)) " +
			"|| " +
			"annotatedByLoggable() && execution(* ru.patseev.monitoringservice.controller.AuditController.*(..))")
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
	@Around("annotatedByLoggable() && execution(* ru.patseev.monitoringservice.controller.UserController.*(..)) ")
	public Object loggingAuthAndRegister(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		ActionEnum action = getAction(proceedingJoinPoint);

		String jwtToken = (String) result;
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
