package ru.patseev.monitoringservice.in.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.patseev.monitoringservice.exception.*;

/**
 * Controller advice for handling exceptions globally within the application.
 * It provides centralized exception handling for various types of exceptions that may occur during request processing.
 */
@ControllerAdvice
public class ExceptionHandlerController {

	/**
	 * Handles the UserNotFoundException exception.
	 *
	 * @param e The UserNotFoundException object representing the exception.
	 * @return A ResponseEntity with HTTP status 404 (Not Found) and the error message from the exception.
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Handles the UserAlreadyExistException exception.
	 *
	 * @param e The UserAlreadyExistException object representing the exception.
	 * @return A ResponseEntity with HTTP status 409 (Conflict) and the error message from the exception.
	 */
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<?> userAlreadyExistExceptionHandler(UserAlreadyExistException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	/**
	 * Handles the DataMeterNotFoundException exception.
	 *
	 * @param e The DataMeterNotFoundException object representing the exception.
	 * @return A ResponseEntity with HTTP status 404 (Not Found) and the error message from the exception.
	 */
	@ExceptionHandler(DataMeterNotFoundException.class)
	public ResponseEntity<?> dataMeterNotFoundExceptionHandler(DataMeterNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Handles the MeterDataConflictException exception.
	 *
	 * @param e The MeterDataConflictException object representing the exception.
	 * @return A ResponseEntity with HTTP status 409 (Conflict) and the error message from the exception.
	 */
	@ExceptionHandler(MeterDataConflictException.class)
	public ResponseEntity<?> meterDataConflictExceptionHandler(MeterDataConflictException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	/**
	 * Handles the MeterTypeExistException exception.
	 *
	 * @param e The MeterTypeExistException object representing the exception.
	 * @return A ResponseEntity with HTTP status 409 (Conflict) and the error message from the exception.
	 */
	@ExceptionHandler(MeterTypeExistException.class)
	public ResponseEntity<?> meterTypeExistExceptionHandler(MeterTypeExistException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
}