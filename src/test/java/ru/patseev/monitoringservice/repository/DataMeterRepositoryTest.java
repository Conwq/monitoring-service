package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.patseev.monitoringservice.domain.DataMeter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
class DataMeterRepositoryTest {

	@Container
	@ServiceConnection
	@SuppressWarnings("unused")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

	int userId;
	DataMeter hotWaterData;
	DataMeterRepository dataMeterRepository;

	@Autowired
	public DataMeterRepositoryTest(DataMeterRepository dataMeterRepository) {
		this.dataMeterRepository = dataMeterRepository;
	}

	@BeforeEach
	void setUp() {
		LocalDate now = LocalDate.now();
		Timestamp submissionDate = Timestamp.valueOf(String.format("%s-%s-01 00:00:00", now.getYear(), now.getMonth().getValue()));

		userId = 1;
		hotWaterData = new DataMeter(1, submissionDate, 1L, 1, userId);
	}

	@Test
	@DisplayName("saveDataMeter should save the user and successfully retrieve the last sent data")
	void saveDataMeter_shouldSaveDataMeter() {
		dataMeterRepository.saveDataMeter(hotWaterData);
		Optional<DataMeter> actual = dataMeterRepository.findLastDataMeter(userId);

		hotWaterData.setMeterDataId(2);
		assertThat(actual)
				.isPresent();
		assertThat(actual)
				.isEqualTo(Optional.of(hotWaterData));
	}

	@Test
	@DisplayName("findLastDataMeter should return empty Optional")
	void findLastDataMeter_shouldReturnEmptyDataMeter() {
		Optional<DataMeter> actual = dataMeterRepository.findLastDataMeter(100);

		assertThat(actual)
				.isEmpty();
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return a list of meter data for the user for the current month")
	void getMeterDataForSpecifiedMonth_shouldReturnData() {
		dataMeterRepository.saveDataMeter(hotWaterData);
		List<DataMeter> actual = dataMeterRepository
				.getMeterDataForSpecifiedMonth(userId, hotWaterData.getSubmissionDate().toLocalDateTime().getMonthValue());

		hotWaterData.setMeterDataId(1);
		assertThat(actual.size())
				.isEqualTo(1);
		assertThat(actual)
				.isEqualTo(List.of(hotWaterData));
	}

	@Test
	@DisplayName("getMeterDataForSpecifiedMonth should return an empty list of data")
	void getMeterDataForSpecifiedMonth_shouldReturnEmptyList() {
		int currentMonth = LocalDate.now().getMonth().getValue();
		int invalidMonth = currentMonth - 1;
		if (invalidMonth < 1) {
			invalidMonth = currentMonth + 1;
		}

		List<DataMeter> actual = dataMeterRepository.getMeterDataForSpecifiedMonth(userId, invalidMonth);

		assertThat(actual.size())
				.isEqualTo(0);
	}
}