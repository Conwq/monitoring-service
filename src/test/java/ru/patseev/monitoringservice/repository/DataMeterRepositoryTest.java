package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.impl.DataMeterRepositoryImpl;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class DataMeterRepositoryTest {

//	private static final String TEST_USERNAME = "User123";
//	private static DataMeterDatabase dataMeterDatabase;
//	private static DataMeterRepository dataMeterRepository;
//	private DataMeter dataMeter;
//
//	@BeforeAll
//	static void setUp() {
//		dataMeterDatabase = mock(DataMeterDatabase.class);
//		dataMeterRepository = new DataMeterRepositoryImpl(dataMeterDatabase);
//	}
//
//	@BeforeEach
//	void createData() {
//		MeterType meterType = new MeterType(1, "Hot water.");
//		dataMeter = new DataMeter(LocalDate.now(), 1L, meterType);
//	}
//
//	@Test
//	void findLastDataMeter_shouldReturnLastDataMeter() {
//		when(dataMeterDatabase.getLastMeterData(Mockito.anyString()))
//				.thenReturn(Optional.of(dataMeter));
//
//		Optional<DataMeter> actual = dataMeterRepository.findLastDataMeter(TEST_USERNAME);
//
//		assertThat(actual).isEqualTo(Optional.of(dataMeter));
//	}
//
//	@Test
//	void saveDataMeter_shouldSaveData() {
//		dataMeterRepository.saveDataMeter(TEST_USERNAME, dataMeter);
//
//		verify(dataMeterDatabase, times(1))
//				.putMeterData(TEST_USERNAME, dataMeter);
//	}
//
//	@Test
//	void getMeterDataForSpecifiedMonth_shouldReturnData() {
//		when(dataMeterDatabase.getMetersDataByMonth(TEST_USERNAME, 1))
//				.thenReturn(List.of(dataMeter));
//
//		List<DataMeter> actual =
//				dataMeterRepository.getMeterDataForSpecifiedMonth(TEST_USERNAME, 1);
//
//		assertThat(actual).isEqualTo(List.of(dataMeter));
//	}
//
//	@Test
//	void getAllMeterData_shouldGetAllData() {
//		List<DataMeter> dataMeterList = List.of(dataMeter);
//		when(dataMeterDatabase.getMeterData(TEST_USERNAME))
//				.thenReturn(dataMeterList);
//
//		List<DataMeter> actual = dataMeterRepository.getAllMeterData(TEST_USERNAME);
//
//		assertThat(actual).isEqualTo(dataMeterList);
//	}
//
//	@Test
//	void getDataFromAllMeterUsers_shouldGetDataFromAllMeterUsers() {
//		Map<String, List<DataMeter>> stringListMap = Map.of(
//				TEST_USERNAME, List.of(dataMeter)
//		);
//
//		when(dataMeterDatabase.getAllMeterData()).thenReturn(stringListMap);
//
//		Map<String, List<DataMeter>> actual = dataMeterRepository.getDataFromAllMeterUsers();
//
//		assertThat(actual).isEqualTo(stringListMap);
//	}
}