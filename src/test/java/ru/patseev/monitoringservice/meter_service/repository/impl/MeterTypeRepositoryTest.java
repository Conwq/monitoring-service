package ru.patseev.monitoringservice.meter_service.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.meter_service.db.MeterTypeDatabase;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.repository.impl.MeterTypeRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class MeterTypeRepositoryTest {

	private static MeterTypeRepository meterTypeRepository;
	private static MeterTypeDatabase meterTypeDatabase;
	private MeterType meterType;

	@BeforeAll
	static void setUp() {
		meterTypeDatabase = mock(MeterTypeDatabase.class);
		meterTypeRepository = new MeterTypeRepositoryImpl(meterTypeDatabase);
	}

	@BeforeEach
	void createData() {
		meterType = new MeterType(1, "hot water");
	}

	@Test
	void findAllMeterType_shouldReturnAllMeterType() {
		when(meterTypeDatabase.getAllMeterType())
				.thenReturn(List.of(meterType));

		List<MeterType> actual = meterTypeRepository.findAllMeterType();

		assertThat(actual)
				.isNotNull();
		assertThat(actual.size())
				.isEqualTo(1);
		assertThat(actual)
				.isEqualTo(List.of(meterType));
	}

	@Test
	void saveMeterType_shouldSaveMeterType() {
		meterTypeRepository.saveMeterType(meterType);

		verify(meterTypeDatabase, times(1))
				.putMeterType(meterType);
	}
}