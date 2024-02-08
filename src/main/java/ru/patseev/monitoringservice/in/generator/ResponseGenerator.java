package ru.patseev.monitoringservice.in.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The ResponseGenerator class generates HTTP responses.
 */
public class ResponseGenerator {

	/**
	 * The object mapper for serializing objects into JSON.
	 */
	private final ObjectMapper objectMapper;

	/**
	 * Constructs a ResponseGenerator instance with the specified object mapper.
	 *
	 * @param objectMapper The object mapper to be used for serialization.
	 */
	public ResponseGenerator(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Generates an HTTP response with the specified status code and object.
	 *
	 * @param resp   The HTTP servlet response.
	 * @param status The status code of the response.
	 * @param object The object to be included in the response body.
	 */
	public void generateResponse(HttpServletResponse resp, int status, Object object) {
		try {
			resp.setStatus(status);
			resp.setContentType("application/json");

			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(objectMapper.writeValueAsBytes(object));
		} catch (IOException e) {
			//todo: handle exception appropriately
			throw new RuntimeException(e);
		}
	}
}

