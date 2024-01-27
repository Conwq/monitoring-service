package ru.patseev.monitoringservice.data_meter_service.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.data_meter_service.db.DataMeterDatabase;
import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class DataMeterRepositoryTest {
	private static final String TEST_USERNAME = "User123";
	private static DataMeterDatabase dataMeterDatabase;
	private static DataMeterRepository dataMeterRepository;
	private DataMeter dataMeter;

	@BeforeAll
	static void setUp() {
		dataMeterDatabase = mock(DataMeterDatabase.class);
		dataMeterRepository = new DataMeterRepositoryImpl(dataMeterDatabase);
	}

	@BeforeEach
	void createDat() {
		dataMeter = new DataMeter(LocalDate.now(), 1L, 1L, 1L, TEST_USERNAME);
	}

	@Test
	void mustFindLastDataMeter() {
		when(dataMeterDatabase.getLastMeterData(Mockito.anyString()))
				.thenReturn(Optional.of(dataMeter));

		Optional<DataMeter> actual = dataMeterRepository.findLastDataMeter(TEST_USERNAME);

		assertThat(actual).isEqualTo(Optional.of(dataMeter));
	}

	@Test
	void mustSaveDataMeter() {
		dataMeterRepository.saveDataMeter(TEST_USERNAME, dataMeter);

		verify(dataMeterDatabase, times(1))
				.putData(TEST_USERNAME, dataMeter);
	}

	@Test
	void mustGetMeterDataForSpecifiedMonth() {
		when(dataMeterDatabase.getMetersDataByMonth(TEST_USERNAME, 1))
				.thenReturn(Optional.of(dataMeter));

		Optional<DataMeter> actual =
				dataMeterRepository.getMeterDataForSpecifiedMonth(TEST_USERNAME, 1);

		assertThat(actual).isEqualTo(Optional.of(dataMeter));
	}

	@Test
	void mustGetAllMeterData() {
		List<DataMeter> dataMeterList = List.of(dataMeter);
		when(dataMeterDatabase.getMeterData(TEST_USERNAME))
				.thenReturn(dataMeterList);

		List<DataMeter> actual = dataMeterRepository.getAllMeterData(TEST_USERNAME);

		assertThat(actual).isEqualTo(dataMeterList);
	}

	@Test
	void mustGetDataFromAllMeterUsers() {
		Map<String, List<DataMeter>> stringListMap = Map.of(
				TEST_USERNAME, List.of(dataMeter)
		);

		when(dataMeterDatabase.getAllMeterData()).thenReturn(stringListMap);

		Map<String, List<DataMeter>> actual = dataMeterRepository.getDataFromAllMeterUsers();

		assertThat(actual).isEqualTo(stringListMap);
	}
}