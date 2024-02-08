package ru.patseev.monitoringservice.service;

import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;

import java.util.List;
import java.util.Map;

/**
 * Interface defining methods for managing data meter readings.
 */
public interface MeterService {

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param userId The ID of the user for whom the current data meter reading is requested.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	DataMeterDto getCurrentDataMeter(int userId);

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param userId       The ID of the user for whom the data meter reading is saved.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	void saveDataMeter(int userId, DataMeterDto dataMeterDto);

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param userId The ID of the user for whom the data meter reading is requested.
	 * @param month  The month for which the data meter reading is requested.
	 * @return Returns a list of all data for the specified month or an empty list.
	 */
	List<DataMeterDto> getMeterDataForSpecifiedMonth(int userId, int month);

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param userId The ID of the user for whom all data meter readings are requested.
	 * @return A list of data meter readings as DataMeterDto objects.
	 */
	List<DataMeterDto> getAllMeterData(int userId);

	/**
	 * Retrieves data from all meter users.
	 *
	 * @return A map containing username as the key and DataMeterDto as the value.
	 */
	Map<String, List<DataMeterDto>> getDataFromAllMeterUsers();

	/**
	 * Retrieves a list of available meter types.
	 *
	 * @return A list of MeterTypeDto representing available meter types.
	 */
	List<MeterTypeDto> getAvailableMeterType();

	/**
	 * Saves a new type meter.
	 *
	 * @param meterTypeDto An object containing the data of the new meter.
	 */
	void saveMeterType(MeterTypeDto meterTypeDto);
}