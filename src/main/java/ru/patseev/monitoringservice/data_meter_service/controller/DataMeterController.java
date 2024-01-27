package ru.patseev.monitoringservice.data_meter_service.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.service.DataMeterService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * The DataMeterController class serves as a controller for managing data meter operations.
 */
@RequiredArgsConstructor
public class DataMeterController {
	private final DataMeterService dataMeterService;
	private final AuditService auditService;

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param userDto The user for whom the current data meter reading is requested.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	public DataMeterDto getCurrentMeterData(UserDto userDto) {
		DataMeterDto currentDataMeter = dataMeterService.getCurrentDataMeter(userDto);
		auditService.saveUserAction(ActionEnum.GET_CURRENT_METER_DATA, userDto);
		return currentDataMeter;
	}

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param userDto      The user for whom the data meter reading is saved.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	public void sendMeterData(UserDto userDto, DataMeterDto dataMeterDto) {
		dataMeterService.saveDataMeter(userDto, dataMeterDto);
		auditService.saveUserAction(ActionEnum.SEND_METER_DATA, userDto);
	}

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param userDto The user for whom the data meter reading is requested.
	 * @param month   The month for which the data meter reading is requested.
	 * @return The data meter reading for the specified month as a DataMeterDto object.
	 */
	public DataMeterDto getMeterDataForSpecifiedMonth(UserDto userDto, int month) {
		DataMeterDto meterDataForSpecifiedMonth = dataMeterService.getMeterDataForSpecifiedMonth(userDto, month);
		auditService.saveUserAction(ActionEnum.GET_METER_DATA_FOR_SPECIFIED_MONTH, userDto);
		return meterDataForSpecifiedMonth;
	}

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param userDto The user for whom all data meter readings are requested.
	 * @return A list of data meter readings as DataMeterDto objects.
	 */
	public List<DataMeterDto> getMeterDataForUser(UserDto userDto) {
		List<DataMeterDto> allMeterDataByUsername = dataMeterService.getAllMeterData(userDto);
		auditService.saveUserAction(ActionEnum.GET_METER_DATA_FOR_USER, userDto);
		return allMeterDataByUsername;
	}

	/**
	 * Retrieves data from all meter users and logs the corresponding user action.
	 *
	 * @param userDto The user DTO.
	 * @return A map containing username as the key and DataMeterDto as the value.
	 */
	public Map<String, List<DataMeterDto>> getDataFromAllMeterUsers(UserDto userDto) {
		Map<String, List<DataMeterDto>> usersMeterData = dataMeterService.getDataFromAllMeterUsers();
		auditService.saveUserAction(ActionEnum.GET_ALL_METER_DATA, userDto);
		return usersMeterData;
	}
}