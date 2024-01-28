package ru.patseev.monitoringservice.data_meter_service.service;

import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.dto.MeterTypeDto;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * Interface defines methods for managing data meter readings.
 */
public interface DataMeterService {

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param userDto The user for whom the current data meter reading is requested.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	DataMeterDto getCurrentDataMeter(UserDto userDto);

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param userDto      The user for whom the data meter reading is saved.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	void saveDataMeter(UserDto userDto, DataMeterDto dataMeterDto);

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param userDto The user for whom the data meter reading is requested.
	 * @param month   The month for which the data meter reading is requested.
	 * @return The data meter reading for the specified month as a DataMeterDto object.
	 */
	DataMeterDto getMeterDataForSpecifiedMonth(UserDto userDto, int month);

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param userDto The user for whom all data meter readings are requested.
	 * @return A list of data meter readings as a DataMeterDto objects.
	 */
	List<DataMeterDto> getAllMeterData(UserDto userDto);

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
	 * Save a new type meter.
	 *
	 * @param meterTypeDto  An object containing the data of the new meter.
	 */
	void saveMeterType(MeterTypeDto meterTypeDto);
}