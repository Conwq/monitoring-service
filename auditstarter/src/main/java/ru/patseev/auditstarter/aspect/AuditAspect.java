package ru.patseev.auditstarter.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import ru.patseev.auditstarter.manager.enums.ActionEnum;
import ru.patseev.auditstarter.manager.ActionManager;
import ru.patseev.auditstarter.service.AuditService;
import ru.patseev.auditstarter.service.JwtAuditService;

import java.util.Arrays;
import java.util.Optional;

/**
 * Aspect for logging user actions and authentication/registration activities.
 */
@Aspect
@RequiredArgsConstructor
public class AuditAspect {

	/**
	 * Service for JWT operations.
	 */
	private final JwtAuditService jwtService;

	/**
	 * Service for audit operations.
	 */
	private final AuditService auditService;

	/**
	 * Manager for handling actions associated with method names.
	 */
	private final ActionManager actionManager;

	/**
	 * Pointcut for methods annotated with @Loggable.
	 */
	@Pointcut("@annotation(ru.patseev.auditstarter.annotation.Audit)")
	public void annotatedByAudit() {
	}

	/**
	 * Advice for audit authentication or registration activities in UserController.
	 *
	 * @param proceedingJoinPoint The proceeding join point
	 * @return The result of the method execution
	 * @throws Throwable If an error occurs during method execution
	 */
	@Around("annotatedByAudit() && execution(* ru.patseev.monitoringservice.in.controller.UserController.*(..)) ")
	public Object auditAuthAndRegister(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		ResponseEntity<?> result = (ResponseEntity<?>) proceedingJoinPoint.proceed();
		ActionEnum action = getAction(proceedingJoinPoint);

		processResponseAndSaveAction(result, action);

		return result;
	}

	/**
	 * Process the response and save audit action.
	 *
	 * @param result The ResponseEntity object representing the HTTP response.
	 * @param action The ActionEnum representing the audit action.
	 */
	private void processResponseAndSaveAction(ResponseEntity<?> result, ActionEnum action) {
		Object body = result.getBody();
		if (body != null) {
			String stringBody = body.toString();
			String jwtToken = extractJwtToken(stringBody);
			if (jwtToken != null) {
				int userId = jwtService.extractPlayerId(jwtToken);
				auditService.saveUserAction(action, userId);
			}
		}
	}

	/**
	 * Extract JWT token from the response body.
	 *
	 * @param responseBody The string representing the response body.
	 * @return The JWT token if found, null otherwise.
	 */
	private String extractJwtToken(String responseBody) {
		if (responseBody.split("\\.").length == 3) {
			return responseBody;
		}
		return null;
	}

	/**
	 * Advice for logging user actions in MeterController and AuditController.
	 *
	 * @param proceedingJoinPoint The proceeding join point
	 * @return The result of the method execution
	 * @throws Throwable If an error occurs during method execution
	 */
	@Around("(annotatedByAudit() && execution(* ru.patseev.monitoringservice.in.controller.MeterController.*(..))) || " +
			"(annotatedByAudit() && execution(* ru.patseev.monitoringservice.in.controller.AuditController.*(..)))")
	public Object auditUserAction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		ActionEnum action = getAction(proceedingJoinPoint);
		Optional<String> optionalJwtToken = extractOptionalJwtToken(proceedingJoinPoint);
		if (optionalJwtToken.isPresent()) {
			int userId = optionalJwtToken
					.map(jwtService::extractPlayerId)
					.orElseThrow(() -> new IllegalStateException("Unable to extractor user ID from arguments."));
			auditService.saveUserAction(action, userId);
		}
		return result;
	}

	/**
	 * Extracts an optional JWT token from the arguments of the intercepted method.
	 * If a JWT token is found among the arguments, it is returned wrapped in an Optional,
	 * otherwise an empty Optional is returned.
	 *
	 * @param proceedingJoinPoint The ProceedingJoinPoint object representing the method being intercepted.
	 * @return An Optional containing the JWT token if found, otherwise an empty Optional.
	 */
	private Optional<String> extractOptionalJwtToken(ProceedingJoinPoint proceedingJoinPoint) {
		return Arrays
				.stream(proceedingJoinPoint.getArgs())
				.filter(o -> o instanceof String)
				.map(Object::toString)
				.filter(str -> str.split("\\.").length == 3)
				.findFirst();
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
