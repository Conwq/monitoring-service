package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.impl.DataMeterRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DataMeterRepositoryTest extends AbstractPostgreSQLContainer {

	private static DataMeterRepository dataMeterRepository;

	private int userId;
	private DataMeter hotWaterData;

	@BeforeAll
	static void beforeAll() {
		ConnectionManager connectionManager = new ConnectionManager(
				POSTGRES.getJdbcUrl(),
				POSTGRES.getUsername(),
				POSTGRES.getPassword()
		);

		ResourceManager resourceManager = new ResourceManager("application");

		new LiquibaseMigration(connectionManager, resourceManager)
				.performMigration();

		dataMeterRepository = new DataMeterRepositoryImpl(connectionManager);
	}

	@BeforeEach
	void setUp() {
		LocalDate now = LocalDate.now();
		Timestamp submissionDate = Timestamp.valueOf(String.format("%s-%s-01 00:00:00", now.getYear(), now.getMonth().getValue()));

		userId = 1;
		hotWaterData = DataMeter.builder()
				.meterDataId(1)
				.submissionDate(submissionDate)
				.value(1L)
				.meterTypeId(1)
				.userId(userId).build();
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