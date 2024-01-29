package ru.patseev.monitoringservice.data_meter_service.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.data_meter_service.domain.MeterType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MeterTypeDatabaseTest {

	private static MeterTypeDatabase meterTypeDatabase;

	@BeforeEach
	void setUp() {
		meterTypeDatabase = new MeterTypeDatabase();
	}

	@Test
	void getAllMeterType_shouldReturnActualMeterType() {
		List<MeterType> actual = meterTypeDatabase.getAllMeterType();

		assertThat(actual)
				.isNotNull();
		assertThat(actual.size())
				.isEqualTo(3);
		assertThat(actual.get(0).getTypeName())
				.isEqualTo("Отопление");
		assertThat(actual.get(1).getTypeName())
				.isEqualTo("Холодная вода");
		assertThat(actual.get(2).getTypeName())
				.isEqualTo("Горячая вода");
	}

	@Test
	void putMeterType_shouldSaveNewMeterType() {
		MeterType meterType = new MeterType(1, "hot water");
		meterTypeDatabase.putMeterType(meterType);

		List<MeterType> allMeterType = meterTypeDatabase.getAllMeterType();

		assertThat(allMeterType)
				.isNotNull();
		assertThat(allMeterType.get(allMeterType.size() - 1))
				.isEqualTo(meterType);
	}
}