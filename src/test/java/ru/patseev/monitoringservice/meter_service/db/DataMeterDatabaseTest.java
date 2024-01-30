package ru.patseev.monitoringservice.meter_service.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.meter_service.domain.MeterType;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DataMeterDatabaseTest {

	private static final String TEST_NAME = "test";
	private DataMeterDatabase dataMeterDatabase;
	private DataMeter dataMeter;

	@BeforeEach
	void setUp() {
		dataMeterDatabase = new DataMeterDatabase();
		dataMeter = new DataMeter(LocalDate.now(), 1L, new MeterType(1, "Hot water"));
	}

	@Test
	void getLastMeterData_shouldReturnLastMeterData() {
		dataMeterDatabase.putMeterData(TEST_NAME, dataMeter);

		Optional<DataMeter> actual = dataMeterDatabase.getLastMeterData(TEST_NAME);

		assertThat(actual)
				.isPresent();
		assertThat(actual.get())
				.isEqualTo(dataMeter);
	}

	@Test
	void getLastMeterData_shouldReturnEmptyIfMeterDataNotExist() {
		Optional<DataMeter> actual = dataMeterDatabase.getLastMeterData(TEST_NAME);

		assertThat(actual)
				.isEmpty();
	}

	@Test
	void putMeterData_shouldSaveNewMeterData() {
		dataMeterDatabase.putMeterData(TEST_NAME, dataMeter);

		Optional<DataMeter> actual = dataMeterDatabase.getLastMeterData(TEST_NAME);

		assertThat(actual)
				.isPresent();
		assertThat(actual.get())
				.isEqualTo(dataMeter);
	}

	@Test
	void getMetersDataByMonth_shouldReturnMeterDataByMonth() {
		MeterType newMeterType = new MeterType(2, "test type");
		DataMeter newDataMeter = new DataMeter(LocalDate.now(), 2L, newMeterType);
		dataMeterDatabase.putMeterData(TEST_NAME, dataMeter);
		dataMeterDatabase.putMeterData(TEST_NAME, newDataMeter);

		List<DataMeter> actual =
				dataMeterDatabase.getMetersDataByMonth(TEST_NAME, LocalDate.now().getMonth().getValue());

		assertThat(actual)
				.isEqualTo(List.of(dataMeter, newDataMeter));
	}

	@Test
	void getMeterData_shouldReturnUsersMeterData() {
		MeterType newMeterType = new MeterType(2, "test type");
		DataMeter newDataMeter = new DataMeter(
				LocalDate.of(
						LocalDate.now().getYear(),
						Month.APRIL,
						2
				), 2L, newMeterType);
		dataMeterDatabase.putMeterData(TEST_NAME, dataMeter);
		dataMeterDatabase.putMeterData(TEST_NAME, newDataMeter);

		List<DataMeter> actual = dataMeterDatabase.getMeterData(TEST_NAME);

		assertThat(actual)
				.isEqualTo(List.of(dataMeter, newDataMeter));
	}

	@Test
	void getAllMeterData_shouldReturnAllUserMeterData() {
		final String testUsername = "user123";

		dataMeterDatabase.putMeterData(TEST_NAME, dataMeter);
		dataMeterDatabase.putMeterData(testUsername, dataMeter);

		Map<String, List<DataMeter>> actual = dataMeterDatabase.getAllMeterData();

		assertThat(actual)
				.isEqualTo(
						Map.of(
								TEST_NAME, List.of(dataMeter),
								testUsername, List.of(dataMeter)));
	}
}