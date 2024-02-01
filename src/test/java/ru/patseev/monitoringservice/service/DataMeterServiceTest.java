package ru.patseev.monitoringservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.service.impl.MeterServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DataMeterServiceTest {

	private static DataMeterRepository dataMeterRepository;
	private static MeterTypeRepository meterTypeRepository;
	private static MeterService dataMeterService;
	private UserDto userDto;
	private DataMeter dataMeter;
	private DataMeterDto dataMeterDto;
	private MeterType meterType;
	private MeterTypeDto meterTypeDto;

	@BeforeAll
	static void setUp() {
		dataMeterRepository = Mockito.mock(DataMeterRepository.class);
		meterTypeRepository = Mockito.mock(MeterTypeRepository.class);
		dataMeterService = new MeterServiceImpl(dataMeterRepository, meterTypeRepository);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		meterType = new MeterType(1, "Hot water.");
		meterTypeDto = new MeterTypeDto(meterType.getMeterTypeId(), meterType.getTypeName());
		dataMeter = new DataMeter(
				1,
				Timestamp.valueOf(LocalDate.now().atStartOfDay()),
				1L,
				meterType.getMeterTypeId(),
				userDto.userId()
		);
		dataMeterDto = new DataMeterDto(LocalDate.now(), 1L, 1, "Hot water.");
	}

	@Test
	void getCurrentDataMeter_shouldReturnDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.userId()))
				.thenReturn(Optional.of(dataMeter));

		DataMeterDto actual = dataMeterService.getCurrentDataMeter(userDto);


		assertThat(actual).isEqualTo(dataMeterDto);
	}

	@Test
	void getCurrentDataMeter_shouldThrowExceptionWhenGetNonExistentDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.userId()))
				.thenReturn(Optional.empty());

		assertThrows(DataMeterNotFoundException.class,
				() -> dataMeterService.getCurrentDataMeter(userDto));
	}

	@Test
	void saveDataMeter_shouldSaveData() {
		dataMeterService.saveDataMeter(userDto, dataMeterDto);

		verify(dataMeterRepository, Mockito.times(1))
				.saveDataMeter(dataMeter);
	}

	@Test
	void getMeterDataForSpecifiedMonth_shouldReturnData() {
		when(dataMeterRepository.getMeterDataForSpecifiedMonth(userDto.userId(), LocalDate.now().getMonth().getValue()))
				.thenReturn(List.of(dataMeter));

		List<DataMeterDto> actual =
				dataMeterService.getMeterDataForSpecifiedMonth(userDto, LocalDate.now().getMonth().getValue());

		assertThat(actual)
				.isEqualTo(List.of(dataMeterDto));
	}

	@Test
	void getAllMeterData_shouldReturnData() {
		when(dataMeterRepository.getAllMeterData(userDto.userId()))
				.thenReturn(List.of(dataMeter));

		List<DataMeterDto> actual = dataMeterService.getAllMeterData(userDto);

		assertThat(actual)
				.isEqualTo(List.of(dataMeterDto));
	}

	@Test
	void getAllMeterData_shouldReturnEmptyList() {
		when(dataMeterRepository.getAllMeterData(userDto.userId()))
				.thenReturn(Collections.emptyList());

		List<DataMeterDto> actual = dataMeterService.getAllMeterData(userDto);

		assertThat(actual.size())
				.isEqualTo(0);
	}

	@Test
	void getDataFromAllMeterUsers_shouldReturnAllMeterUsers() {
		when(dataMeterRepository.getDataFromAllMeterUsers())
				.thenReturn(Map.of(userDto.userId(), List.of(dataMeter)));

		Map<String, List<DataMeterDto>> actual = dataMeterService.getDataFromAllMeterUsers();

		assertThat(actual)
				.isEqualTo(Map.of(userDto.username(), List.of(dataMeterDto)));
	}

	@Test
	void getDataFromAllMeterUsers_shouldReturnEmptyMapMeterUsers() {
		when(dataMeterRepository.getDataFromAllMeterUsers())
				.thenReturn(Collections.emptyMap());

		Map<String, List<DataMeterDto>> actual = dataMeterService.getDataFromAllMeterUsers();

		assertThat(actual.size())
				.isEqualTo(0);
	}

	@Test
	void getAvailableMeterType_shouldReturnAvailableMeterType() {
		when(meterTypeRepository.findAllMeterType())
				.thenReturn(List.of(meterType));

		List<MeterTypeDto> actual = dataMeterService.getAvailableMeterType();

		assertThat(actual.size())
				.isEqualTo(1);
		assertThat(actual)
				.isEqualTo(List.of(meterTypeDto));
	}

	@Test
	void saveMeterType_shouldSaveMeterType() {
		dataMeterService.saveMeterType(meterTypeDto);

		verify(meterTypeRepository, times(1))
				.saveMeterType(meterType);
	}
}