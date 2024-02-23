package ru.patseev.monitoringservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.patseev.monitoringservice.config.annotation.DisableLiquibaseMigration;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisableLiquibaseMigration
class MeterServiceTest {

	@MockBean
	DataMeterRepository dataMeterRepository;
	@MockBean
	MeterTypeRepository meterTypeRepository;
	MeterService meterService;
	UserDto userDto;
	DataMeter dataMeter;
	DataMeterDto dataMeterDto;
	MeterType meterType;
	MeterTypeDto meterTypeDto;

	@Autowired
	public MeterServiceTest(MeterService meterService) {
		this.meterService = meterService;
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		meterType = new MeterType(1, "hot water");
		meterTypeDto = new MeterTypeDto(meterType.getMeterTypeId(), meterType.getTypeName());
		dataMeter = new DataMeter(
				1,
				Timestamp.valueOf(LocalDate.now().atStartOfDay()),
				1L,
				meterType.getMeterTypeId(),
				userDto.userId()
		);
		dataMeterDto = new DataMeterDto(
				Timestamp.valueOf(LocalDate.now().atStartOfDay()),
				1L,
				1,
				meterType.getTypeName()
		);
	}

	@Test
	@DisplayName("getCurrentDataMeter should return last DataMeter")
	void getCurrentDataMeter_shouldReturnDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.userId()))
				.thenReturn(Optional.of(dataMeter));
		when(meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId()))
				.thenReturn(meterType);

		DataMeterDto actual = meterService.getCurrentDataMeter(userDto.userId());

		assertThat(actual)
				.isEqualTo(dataMeterDto);
	}

	@Test
	@DisplayName("getCurrentDate Meter should throw an exception when receiving a non-existent data meter")
	void getCurrentDataMeter_shouldThrowExceptionWhenGetNonExistentDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.userId()))
				.thenReturn(Optional.empty());

		assertThrows(DataMeterNotFoundException.class,
				() -> meterService.getCurrentDataMeter(userDto.userId()));
	}

	@Test
	@DisplayName("saveDataMeter should save data meter in db")
	void saveDataMeter_shouldSaveData() {
		meterService.saveDataMeter(userDto.userId(), dataMeterDto);

		verify(dataMeterRepository)
				.saveDataMeter(any(DataMeter.class));
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return meter data for the specified month")
	void getMeterDataForSpecifiedMonth_shouldReturnData() {
		when(dataMeterRepository.getMeterDataForSpecifiedMonth(userDto.userId(), LocalDate.now().getMonth().getValue()))
				.thenReturn(List.of(dataMeter));
		when(meterTypeRepository.getMeterTypeById(meterType.getMeterTypeId()))
				.thenReturn(meterType);

		List<DataMeterDto> actual =
				meterService.getMeterDataForSpecifiedMonth(userDto.userId(), LocalDate.now().getMonth().getValue());

		System.out.println(actual);

		assertThat(actual)
				.isEqualTo(List.of(dataMeterDto));
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return the meter data")
	void getAllMeterData_shouldReturnData() {
		when(dataMeterRepository.getAllMeterData(userDto.userId()))
				.thenReturn(List.of(dataMeter));
		when(meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId()))
				.thenReturn(meterType);

		List<DataMeterDto> actual = meterService.getUserMeterData(userDto.userId());

		assertThat(actual)
				.isEqualTo(List.of(dataMeterDto));
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return an empty list of meter data")
	void getAllMeterData_shouldReturnEmptyList() {
		when(dataMeterRepository.getAllMeterData(userDto.userId()))
				.thenReturn(Collections.emptyList());

		List<DataMeterDto> actual = meterService.getUserMeterData(userDto.userId());

		assertThat(actual.size())
				.isEqualTo(0);
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return a list of all users' counter data")
	void getDataFromAllMeterUsers_shouldReturnAllMeterUsers() {
		when(dataMeterRepository.getDataFromAllMeterUsers())
				.thenReturn(Map.of(userDto.userId(), List.of(dataMeter)));
		when(meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId()))
				.thenReturn(meterType);

		Map<String, List<DataMeterDto>> actual = meterService.getDataFromAllMeterUsers();

		assertThat(actual)
				.isEqualTo(Map.of(userDto.userId().toString(), List.of(dataMeterDto)));
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return an empty list of all users' counter data")
	void getDataFromAllMeterUsers_shouldReturnEmptyMapMeterUsers() {
		when(dataMeterRepository.getDataFromAllMeterUsers())
				.thenReturn(Collections.emptyMap());

		Map<String, List<DataMeterDto>> actual = meterService.getDataFromAllMeterUsers();

		assertThat(actual.size())
				.isEqualTo(0);
	}

	@Test
	@DisplayName("getAvailableMeterType should return a list of available meter types")
	void getAvailableMeterType_shouldReturnAvailableMeterType() {
		when(meterTypeRepository.findAllMeterType())
				.thenReturn(List.of(meterType));

		List<MeterTypeDto> actual = meterService.getAvailableMeterType();

		assertThat(actual.size())
				.isEqualTo(1);
		assertThat(actual)
				.isEqualTo(List.of(meterTypeDto));
	}

	@Test
	@DisplayName("getAvailableMeterType should save the new meter type")
	void saveMeterType_shouldSaveMeterType() {
		meterService.saveMeterType(meterTypeDto);

		verify(meterTypeRepository, times(1))
				.saveMeterType(meterType);
	}
}