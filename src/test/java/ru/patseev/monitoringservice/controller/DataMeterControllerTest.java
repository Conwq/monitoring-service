package ru.patseev.monitoringservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.MeterService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class DataMeterControllerTest {

	private static MeterService dataMeterService;
	private static AuditService auditService;
	private static MeterController dataMeterController;

	private UserDto userDto;
	private DataMeterDto dataMeterDto;
	private List<DataMeterDto> dataMeterDtoList;
	private MeterTypeDto meterTypeDto;

	@BeforeAll
	static void setUp() {
		dataMeterService = Mockito.mock(MeterService.class);
		auditService = Mockito.mock(AuditService.class);
		dataMeterController = new MeterController(dataMeterService, auditService);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		dataMeterDto = new DataMeterDto(LocalDate.now(), 1L, 1, "Hot water.");
		dataMeterDtoList = new ArrayList<>() {{
			add(dataMeterDto);
			add(dataMeterDto);
		}};
		meterTypeDto = new MeterTypeDto(1, "Hot water");
	}

	@Test
	@DisplayName("getCurrentMetricData should return last submit Meter Data")
	void getCurrentMeterData_shouldReturnData() {
		when(dataMeterService.getCurrentDataMeter(userDto))
				.thenReturn(dataMeterDto);

		DataMeterDto actual = dataMeterController.getCurrentMeterData(userDto);

		assertThat(actual)
				.isEqualTo(dataMeterDto);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_CURRENT_METER_DATA, userDto);
	}

	@Test
	@DisplayName("saveMeterData should save MeterData")
	void saveMeterData_shouldSaveMeterData() {
		dataMeterController.saveMeterData(userDto, dataMeterDto);

		verify(dataMeterService, times(1))
				.saveDataMeter(userDto, dataMeterDto);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.SEND_METER_DATA, userDto);
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return users MeterData for specified month")
	void getMeterDataForSpecifiedMonth_shouldReturnDataForSpecifiedMonth() {
		when(dataMeterService.getMeterDataForSpecifiedMonth(userDto, 1))
				.thenReturn(dataMeterDtoList);

		List<DataMeterDto> actual = dataMeterController.getMeterDataForSpecifiedMonth(userDto, 1);

		assertThat(actual)
				.isEqualTo(dataMeterDtoList);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_METER_DATA_FOR_SPECIFIED_MONTH, userDto);
	}

	@Test
	@DisplayName("getMeterDataForUser should return list MeterData for specified user")
	void getMeterDataForUser_shouldReturnData() {
		when(dataMeterService.getAllMeterData(userDto))
				.thenReturn(dataMeterDtoList);

		List<DataMeterDto> actual = dataMeterController.getMeterDataForUser(userDto);

		assertThat(actual)
				.isEqualTo(dataMeterDtoList);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_METER_DATA_FOR_USER, userDto);
	}

	@Test
	@DisplayName("getDataFromAllMeter user should return a map with a list of all his data")
	void getDataFromAllMeterUsers_shouldReturnData() {
		Map<String, List<DataMeterDto>> expected = new HashMap<>() {{
			put(userDto.username(), dataMeterDtoList);
		}};
		when(dataMeterService.getDataFromAllMeterUsers())
				.thenReturn(expected);

		Map<String, List<DataMeterDto>> actual = dataMeterController.getDataFromAllMeterUsers(userDto);

		assertThat(actual)
				.isEqualTo(expected);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_ALL_METER_DATA, userDto);
	}

	@Test
	@DisplayName("getAvailableMeterType should return MeterType")
	void getAvailableMeterType_shouldReturnAvailableMeterType() {
		when(dataMeterService.getAvailableMeterType())
				.thenReturn(List.of(meterTypeDto));

		List<MeterTypeDto> actual = dataMeterController.getAvailableMeterType(userDto);

		assertThat(actual)
				.isEqualTo(List.of(meterTypeDto));
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_ACTUAL_METER_TYPE, userDto);
	}

	@Test
	@DisplayName("addNewMeterType should save MeterType")
	void addNewMeterType_shouldSaveNewMeterType() {
		dataMeterController.addNewMeterType(userDto, meterTypeDto);

		verify(dataMeterService, times(1))
				.saveMeterType(meterTypeDto);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.ADD_NEW_METER_TYPE, userDto);
	}
}