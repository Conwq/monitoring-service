package ru.patseev.monitoringservice.manager;

import java.util.ResourceBundle;

/**
 * The ResourceManager class is responsible for managing resources such as property files.
 * It provides methods to retrieve values associated with keys from the resource bundle.
 */
public class ResourceManager {

	/**
	 * The resource bundle containing key-value pairs.
	 */
	private final ResourceBundle resourceBundle;

	/**
	 * Constructs a ResourceManager instance for a specified resource file.
	 *
	 * @param fileName The name of the resource file (without extension) to be loaded.
	 */
	public ResourceManager(String fileName) {
		resourceBundle = ResourceBundle.getBundle(fileName);
	}

	/**
	 * Retrieves the value associated with a given key from the resource bundle.
	 *
	 * @param key The key for which to retrieve the associated value.
	 * @return The value associated with the given key.
	 */
	public String getValue(String key) {
		return resourceBundle.getString(key.toLowerCase());
	}
}