package ru.patseev.monitoringservice.in.generator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * The ResponseGenerator class generates HTTP responses.
 */
@Component
public class ResponseGenerator {

	/**
	 * Generates an HTTP response with the specified status code and object.
	 *
	 * @param httpStatus The status code of the response.
	 * @param object The object to be included in the response body.
	 */
	public ResponseEntity<?> generateResponse(HttpStatus httpStatus, Object object) {
		return new ResponseEntity<>(object, httpStatus);
	}
}