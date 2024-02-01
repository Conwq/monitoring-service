package ru.patseev.monitoringservice.data_meter_service.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.service.DataMeterService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DataMeterControllerTest {
	private static DataMeterService dataMeterService;
	private static AuditService auditService;
	private static DataMeterController dataMeterController;
	private UserDto userDto;
	private DataMeterDto dataMeterDto;
	private List<DataMeterDto> dataMeterDtoList;

	@BeforeAll
	static void setUp() {
		dataMeterService = Mockito.mock(DataMeterService.class);
		auditService = Mockito.mock(AuditService.class);
		dataMeterController = new DataMeterController(dataMeterService, auditService);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto("test", "test", Role.USER);
		dataMeterDto = new DataMeterDto(LocalDate.now(), 1L, 1, "Hot water.");
		dataMeterDtoList = new ArrayList<>() {{
			add(dataMeterDto);
			add(dataMeterDto);
		}};
	}

	@Test
	void mustGetCurrentMeterData() {
		Mockito.when(dataMeterService.getCurrentDataMeter(userDto))
				.thenReturn(dataMeterDto);

		DataMeterDto actual = dataMeterController.getCurrentMeterData(userDto);

		AssertionsForClassTypes.assertThat(actual)
				.isEqualTo(dataMeterDto);
		Mockito.verify(auditService, Mockito.atLeastOnce())
				.saveUserAction(ActionEnum.GET_CURRENT_METER_DATA, userDto);
	}

	@Test
	void mustSendMeterData() {
		dataMeterController.saveMeterData(userDto, dataMeterDto);

		Mockito.verify(dataMeterService, Mockito.times(1))
				.saveDataMeter(userDto, dataMeterDto);
		Mockito.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.SEND_METER_DATA, userDto);
	}

	@Test
	void mustGetMeterDataForSpecifiedMonth() {
		Mockito.when(dataMeterService.getMeterDataForSpecifiedMonth(userDto, 1))
				.thenReturn(dataMeterDto);

		DataMeterDto actual = dataMeterController.getMeterDataForSpecifiedMonth(userDto, 1);

		AssertionsForClassTypes.assertThat(actual)
				.isEqualTo(dataMeterDto);
		Mockito.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.GET_METER_DATA_FOR_SPECIFIED_MONTH, userDto);
	}

	@Test
	void mustGetMeterDataForUserByUsername() {
		Mockito.when(dataMeterService.getAllMeterData(userDto))
				.thenReturn(dataMeterDtoList);

		List<DataMeterDto> actual = dataMeterController.getMeterDataForUser(userDto);

		AssertionsForClassTypes.assertThat(actual).isEqualTo(dataMeterDtoList);
		Mockito.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.GET_METER_DATA_FOR_USER, userDto);
	}

	@Test
	void mustGetDataFromAllMeterUsers() {
		Map<String, List<DataMeterDto>> expected = new HashMap<>() {{
			put(userDto.username(), dataMeterDtoList);
		}};

		Mockito.when(dataMeterService.getDataFromAllMeterUsers())
				.thenReturn(expected);

		Map<String, List<DataMeterDto>> actual = dataMeterController.getDataFromAllMeterUsers(userDto);

		AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
		Mockito.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.GET_ALL_METER_DATA, userDto);
	}
}