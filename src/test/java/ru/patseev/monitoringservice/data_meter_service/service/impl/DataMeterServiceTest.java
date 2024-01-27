package ru.patseev.monitoringservice.data_meter_service.service.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;
import ru.patseev.monitoringservice.data_meter_service.service.DataMeterService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

class DataMeterServiceTest {
	private static DataMeterRepository dataMeterRepository;
	private static DataMeterService dataMeterService;
	private UserDto userDto;
	private DataMeter dataMeter;
	private DataMeterDto dataMeterDto;

	@BeforeAll
	static void setUp() {
		dataMeterRepository = Mockito.mock(DataMeterRepository.class);
		dataMeterService = new DataMeterServiceImpl(dataMeterRepository);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto("test", "test", Role.USER);
		dataMeter = new DataMeter(LocalDate.now(), 1L, 1L, 1L, userDto.username());
		dataMeterDto = new DataMeterDto(LocalDate.now(), 1L, 1L, 1L);
	}

	@Test
	void mustGetCurrentDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.username()))
				.thenReturn(Optional.of(dataMeter));

		DataMeterDto actual = dataMeterService.getCurrentDataMeter(userDto);


		AssertionsForClassTypes.assertThat(actual)
				.isEqualTo(dataMeterDto);
	}

	@Test
	void mustThrowExceptionWhenGetNonExistentDataMeter() {
		when(dataMeterRepository.findLastDataMeter(userDto.username()))
				.thenReturn(Optional.empty());

		Assertions.assertThrows(DataMeterNotFoundException.class,
				() -> dataMeterService.getCurrentDataMeter(userDto));
	}
}