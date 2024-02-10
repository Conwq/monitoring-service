package ru.patseev.monitoringservice.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.MeterService;

import java.util.List;
import java.util.Map;

/**
 * The MeterController class serves as a controller for managing data meter operations.
 */
@RequiredArgsConstructor
public class MeterController {

	/**
	 * The service responsible for data meter-related business logic.
	 */
	private final MeterService meterService;

	/**
	 * The service responsible for auditing user actions.
	 */
	private final AuditService auditService;

	/**
	 * The JWT service responsible for user authentication and token management.
	 */
	private final JwtService jwtService;

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	public DataMeterDto getLatestMeterData(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);

		DataMeterDto currentDataMeter = meterService.getCurrentDataMeter(userId);
		auditService.saveUserAction(ActionEnum.GET_LATEST_METER_DATA, userId);
		return currentDataMeter;
	}

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	public void saveMeterData(String jwtToken, DataMeterDto dataMeterDto) {
		int userId = jwtService.extractPlayerId(jwtToken);

		meterService.saveDataMeter(userId, dataMeterDto);
		auditService.saveUserAction(ActionEnum.SAVE_METER_DATA, userId);
	}

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @param month    The month for which the data meter reading is requested.
	 * @return Returns a list of all data for the specified month or an empty list.
	 */
	public List<DataMeterDto> getMeterDataForSpecifiedMonth(String jwtToken, String month) {
		int userId = jwtService.extractPlayerId(jwtToken);
		int monthNumber = Integer.parseInt(month);

		List<DataMeterDto> meterDataForSpecifiedMonth = meterService.getMeterDataForSpecifiedMonth(userId, monthNumber);
		auditService.saveUserAction(ActionEnum.GET_METER_DATA_FOR_SPECIFIED_MONTH, userId);
		return meterDataForSpecifiedMonth;
	}

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of data meter readings as DataMeterDto objects.
	 */
	public List<DataMeterDto> getMeterDataForUser(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);

		List<DataMeterDto> allMeterDataByUsername = meterService.getUserMeterData(userId);
		auditService.saveUserAction(ActionEnum.GET_METER_DATA_FOR_USER, userId);
		return allMeterDataByUsername;
	}

	/**
	 * Retrieves data from all meter users and logs the corresponding user action.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A map containing username as the key and DataMeterDto as the value.
	 */
	public Map<String, List<DataMeterDto>> getDataFromAllMeterUsers(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);

		Map<String, List<DataMeterDto>> usersMeterData = meterService.getDataFromAllMeterUsers();
		auditService.saveUserAction(ActionEnum.GET_DATA_FROM_ALL_METER_USER, userId);
		return usersMeterData;
	}

	/**
	 * Retrieves a list of available meter types.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of MeterTypeDto representing available meter types.
	 */
	public List<MeterTypeDto> getAvailableMeterType(String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);

		List<MeterTypeDto> availableMeterType = meterService.getAvailableMeterType();
		auditService.saveUserAction(ActionEnum.GET_AVAILABLE_METER_TYPE, userId);
		return availableMeterType;
	}

	/**
	 * Save a new type meter.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param meterTypeDto An object containing the data of the new meter.
	 */
	public void addNewMeterType(String jwtToken, MeterTypeDto meterTypeDto) {
		int userId = jwtService.extractPlayerId(jwtToken);

		meterService.saveMeterType(meterTypeDto);
		auditService.saveUserAction(ActionEnum.ADD_NEW_METER_TYPE, userId);
	}
}