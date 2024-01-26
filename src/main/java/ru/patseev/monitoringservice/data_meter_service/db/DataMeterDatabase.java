package ru.patseev.monitoringservice.data_meter_service.db;

import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The DataMeterDatabase class represents a database for storing data meter readings.
 */
public class DataMeterDatabase {

	private final Map<String, List<DataMeter>> dataMeters;

	public DataMeterDatabase() {
		this.dataMeters = new HashMap<>();
	}

	/**
	 * Retrieves the last data meter reading for the specified user.
	 *
	 * @param username The username for which the last data meter reading is requested.
	 * @return An optional containing the last data meter reading as a DataMeter object, or empty if not found.
	 */
	public Optional<DataMeter> getLastMeterData(String username) {
		List<DataMeter> usersDataMeters = dataMeters.get(username);
		if (usersDataMeters == null) {
			return Optional.empty();
		}
		return Optional.of(usersDataMeters.get(usersDataMeters.size() - 1));
	}

	/**
	 * Adds a new data meter reading for the specified user.
	 *
	 * @param username  The username for which the data meter reading is added.
	 * @param dataMeter The data meter reading to be added.
	 */
	public void putData(String username, DataMeter dataMeter) {
		if (!dataMeters.containsKey(username)) {
			dataMeters.put(username, new ArrayList<>());
		}
		dataMeters.get(username).add(dataMeter);
	}

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param username The username for which the data meter reading is requested.
	 * @param month    The month for which the data meter reading is requested.
	 * @return An optional containing the data meter reading for the specified month as a DataMeter object, or empty if not found.
	 */
	public Optional<DataMeter> getMetersDataByMonth(String username, int month) {
		List<DataMeter> usersDataMeters = dataMeters.get(username);
		if (usersDataMeters == null) {
			usersDataMeters = new ArrayList<>();
		}

		return usersDataMeters.stream()
				.filter(dataMeter -> dataMeter.getDate().getMonth().getValue() == month)
				.findAny();
	}

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param username The username for which all data meter readings are requested.
	 * @return A list of data meter readings as DataMeter objects.
	 */
	public List<DataMeter> getMeterData(String username) {
		List<DataMeter> usersMeterData = dataMeters.get(username);
		if (usersMeterData == null) {
			return new ArrayList<>();
		}
		return usersMeterData;
	}

	/**
	 * Retrieves all meter data.
	 *
	 * @return A map containing username as the key and a list of DataMeter as the value.
	 */
	public Map<String, List<DataMeter>> getAllMeterData() {
		return dataMeters;
	}
}