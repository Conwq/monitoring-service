package ru.patseev.monitoringservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.in.controller.MeterController;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.service.MeterService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class MeterControllerTest {

	private static MeterService dataMeterService;
	private static JwtService jwtService;
	private static MeterController dataMeterController;

	private UserDto userDto;
	private DataMeterDto dataMeterDto;
	private List<DataMeterDto> dataMeterDtoList;
	private MeterTypeDto meterTypeDto;

	@BeforeAll
	static void setUp() {
		dataMeterService = mock(MeterService.class);
		jwtService = mock(JwtService.class);
		ResponseGenerator responseGenerator = mock(ResponseGenerator.class);
		dataMeterController = new MeterController(dataMeterService, jwtService, responseGenerator);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		dataMeterDto = new DataMeterDto(Timestamp.from(Instant.now()), 1L, 1, "Hot water.");
		dataMeterDtoList = new ArrayList<>() {{
			add(dataMeterDto);
			add(dataMeterDto);
		}};
		meterTypeDto = new MeterTypeDto(1, "Hot water");
	}

	@Test
	@DisplayName("getCurrentMetricData should return last submit Meter Data")
	void getCurrentMeterData_shouldReturnData() {
		when(dataMeterService.getCurrentDataMeter(userDto.userId()))
				.thenReturn(dataMeterDto);

		//todo
//		DataMeterDto actual = dataMeterController.getLatestMeterData("auth_token");
//
//		assertThat(actual)
//				.isEqualTo(dataMeterDto);
	}

	@Test
	@DisplayName("saveMeterData should save MeterData")
	void saveMeterData_shouldSaveMeterData() {
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());
		dataMeterController.saveMeterData("auth_token", dataMeterDto);

		verify(dataMeterService)
				.saveDataMeter(userDto.userId(), dataMeterDto);
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return users MeterData for specified month")
	void getMeterDataForSpecifiedMonth_shouldReturnDataForSpecifiedMonth() {
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());
		when(dataMeterService.getMeterDataForSpecifiedMonth(userDto.userId(), 1))
				.thenReturn(dataMeterDtoList);

		//todo
//		List<DataMeterDto> actual = dataMeterController.getMeterDataForSpecifiedMonth("auth_token", "1");
//
//		assertThat(actual)
//				.isEqualTo(dataMeterDtoList);
	}

	@Test
	@DisplayName("getMeterDataForUser should return list MeterData for specified user")
	void getMeterDataForUser_shouldReturnData() {
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());
		when(dataMeterService.getUserMeterData(userDto.userId()))
				.thenReturn(dataMeterDtoList);

		//todo
//		List<DataMeterDto> actual = dataMeterController.getMeterDataForUser("auth_token");
//
//		assertThat(actual)
//				.isEqualTo(dataMeterDtoList);
	}

	@Test
	@DisplayName("getDataFromAllMeter user should return a map with a list of all his data")
	void getDataFromAllMeterUsers_shouldReturnData() {
		Map<String, List<DataMeterDto>> expected = new HashMap<>() {{
			put(userDto.username(), dataMeterDtoList);
		}};
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());
		when(dataMeterService.getDataFromAllMeterUsers())
				.thenReturn(expected);

		//todo
//		Map<String, List<DataMeterDto>> actual = dataMeterController.getDataFromAllMeterUsers("auth_token");
//
//		assertThat(actual)
//				.isEqualTo(expected);
	}

	@Test
	@DisplayName("getAvailableMeterType should return MeterType")
	void getAvailableMeterType_shouldReturnAvailableMeterType() {
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());
		when(dataMeterService.getAvailableMeterType())
				.thenReturn(List.of(meterTypeDto));
		//todo
//		List<MeterTypeDto> actual = dataMeterController.getAvailableMeterType("auth_token");
//
//		assertThat(actual)
//				.isEqualTo(List.of(meterTypeDto));
	}

	@Test
	@DisplayName("addNewMeterType should save MeterType")
	void addNewMeterType_shouldSaveNewMeterType() {
		when(jwtService.extractPlayerId(anyString()))
				.thenReturn(userDto.userId());

		dataMeterController.addNewMeterType("token", meterTypeDto);

		verify(dataMeterService)
				.saveMeterType(meterTypeDto);
	}
}