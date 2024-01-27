package ru.patseev.monitoringservice.data_meter_service.repository;

import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface defines methods for interacting with the data meter storage.
 */
public interface DataMeterRepository {

	/**
	 * Retrieves the last data meter reading for the specified user.
	 *
	 * @param username The username for whom the last data meter reading is requested.
	 * @return An optional containing the last data meter reading as a DataMeter object, or empty if not found.
	 */
	Optional<DataMeter> findLastDataMeter(String username);

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param username  The username for whom the data meter reading is saved.
	 * @param dataMeter The data meter reading to be saved.
	 */
	void saveDataMeter(String username, DataMeter dataMeter);

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param username The username for whom the data meter reading is requested.
	 * @param month    The month for which the data meter reading is requested.
	 * @return An optional containing the data meter reading for the specified month as a DataMeter object, or empty if not found.
	 */
	Optional<DataMeter> getMeterDataForSpecifiedMonth(String username, int month);

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param username The username for whom all data meter readings are requested.
	 * @return A list of data meter readings as DataMeter objects.
	 */
	List<DataMeter> getAllMeterData(String username);

	/**
	 * Retrieves data from all meter users.
	 *
	 * @return A map containing username as the key and DataMeter as the value.
	 */
	Map<String, List<DataMeter>> getDataFromAllMeterUsers();

}