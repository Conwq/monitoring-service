package ru.patseev.monitoringservice.repository;

import ru.patseev.monitoringservice.domain.DataMeter;

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
	 * @param userId The user ID for whom the last data meter reading is requested.
	 * @return An optional containing the last data meter reading as a DataMeter object, or empty if not found.
	 */
	Optional<DataMeter> findLastDataMeter(int userId);

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param dataMeter The data meter reading to be saved.
	 */
	void saveDataMeter(DataMeter dataMeter);

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param userId The user ID for whom the data meter reading is requested.
	 * @param month  The month for which the data meter reading is requested.
	 * @return Returns a list of all data for the specified month or an empty list.
	 */
	List<DataMeter> getMeterDataForSpecifiedMonth(int userId, int month);

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param userId The user ID for whom all data meter readings are requested.
	 * @return A list of data meter readings as DataMeter objects.
	 */
	List<DataMeter> getAllMeterData(int userId);

	/**
	 * Retrieves data from all meter users.
	 *
	 * @return A map containing user ID as the key and a list of DataMeter as the value.
	 */
	Map<Integer, List<DataMeter>> getDataFromAllMeterUsers();
}