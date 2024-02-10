package ru.patseev.monitoringservice.controller;

import ru.patseev.monitoringservice.aspect.annotation.Loggable;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.MeterService;

import java.util.List;
import java.util.Map;

/**
 * The MeterController class serves as a controller for managing data meter operations.
 */
public class MeterController {

	/**
	 * The service responsible for data meter-related business logic.
	 */
	private final MeterService meterService;

	/**
	 * The JWT service responsible for user authentication and token management.
	 */
	private final JwtService jwtService;

	/**
	 * Constructs a new MeterController with the specified dependencies.
	 *
	 * @param meterService The service for meter operations
	 * @param jwtService   The service for JWT operations
	 */
	public MeterController(MeterService meterService, JwtService jwtService) {
		this.meterService = meterService;
		this.jwtService = jwtService;
	}

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	@Loggable
	public DataMeterDto getLatestMeterData(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);
		return meterService.getCurrentDataMeter(userId);
	}

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	@Loggable
	public void saveMeterData(String jwtToken, DataMeterDto dataMeterDto) {
		int userId = jwtService.extractPlayerId(jwtToken);
		meterService.saveDataMeter(userId, dataMeterDto);
	}

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @param month    The month for which the data meter reading is requested.
	 * @return Returns a list of all data for the specified month or an empty list.
	 */
	@Loggable
	public List<DataMeterDto> getMeterDataForSpecifiedMonth(String jwtToken, String month) {
		int userId = jwtService.extractPlayerId(jwtToken);
		int monthNumber = Integer.parseInt(month);
		return meterService.getMeterDataForSpecifiedMonth(userId, monthNumber);
	}

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of data meter readings as DataMeterDto objects.
	 */
	@Loggable
	public List<DataMeterDto> getMeterDataForUser(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);
		return meterService.getUserMeterData(userId);
	}

	/**
	 * Retrieves data from all meter users and logs the corresponding user action.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A map containing username as the key and DataMeterDto as the value.
	 */
	@Loggable
	public Map<String, List<DataMeterDto>> getDataFromAllMeterUsers(String jwtToken) {
		return meterService.getDataFromAllMeterUsers();
	}

	/**
	 * Retrieves a list of available meter types.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of MeterTypeDto representing available meter types.
	 */
	@Loggable
	public List<MeterTypeDto> getAvailableMeterType(String jwtToken) {
		return meterService.getAvailableMeterType();
	}

	/**
	 * Save a new type meter.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param meterTypeDto An object containing the data of the new meter.
	 */
	@Loggable
	public void addNewMeterType(String jwtToken, MeterTypeDto meterTypeDto) {
		meterService.saveMeterType(meterTypeDto);
	}
}