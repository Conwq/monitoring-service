package ru.patseev.monitoringservice.in.extract;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The ObjectExtractor class extracts objects from HTTP requests.
 */
public class ObjectExtractor {

	/**
	 * The object mapper for deserializing JSON into objects.
	 */
	private final ObjectMapper objectMapper;

	/**
	 * Constructs an ObjectExtractor instance with the specified object mapper.
	 *
	 * @param objectMapper The object mapper to be used for deserialization.
	 */
	public ObjectExtractor(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Extracts an object of the specified class from the HTTP request body.
	 *
	 * @param req    The HTTP servlet request.
	 * @param tClass The class of the object to extract.
	 * @param <T>    The type of the object to extract.
	 * @return The extracted object.
	 * @throws RuntimeException If an error occurs during extraction.
	 */
	public <T> T extractObject(HttpServletRequest req, Class<T> tClass) {
		try {
			BufferedReader reader = req.getReader();
			StringBuilder jsonObject = new StringBuilder();
			while (reader.ready()) {
				jsonObject.append(reader.readLine());
			}
			return objectMapper.readValue(jsonObject.toString(), tClass);
		} catch (IOException e) {
			//todo: handle exception appropriately
			throw new RuntimeException(e);
		}
	}
}
