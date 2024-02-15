package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.exception.MeterTypeNotFoundException;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.repository.impl.MeterTypeRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MeterTypeRepositoryTest extends AbstractPostgreSQLContainer {

	static MeterTypeRepository meterTypeRepository;

	@BeforeAll
	static void beforeAll() {
		meterTypeRepository = new MeterTypeRepositoryImpl(DATA_SOURCE, new ConnectionManager());
	}

	@Test
	@DisplayName("saveMeterType should save meter type in database and return value from database")
	void saveMeterType_shouldSaveMeterType() {
		String electricity = "electricity";
		String coldWater = "cold water";

		MeterType typeElectricity = new MeterType(null, electricity);
		MeterType typeColdWater = new MeterType(null, coldWater);

		meterTypeRepository.saveMeterType(typeElectricity);
		meterTypeRepository.saveMeterType(typeColdWater);

		List<MeterType> actual = meterTypeRepository.findAllMeterType();

		assertThat(actual.size())
				.isEqualTo(3);
		assertThat(actual.get(0).getTypeName())
				.isEqualTo("hot water");
		assertThat(actual.get(1).getTypeName())
				.isEqualTo(electricity);
		assertThat(actual.get(2).getTypeName())
				.isEqualTo(coldWater);
	}

	@Test
	@DisplayName("getMeterTypeById should return meter type by id")
	void getMeterTypeById_shouldReturnMeterType() {
		MeterType actual = meterTypeRepository.getMeterTypeById(1);

		assertThat(actual)
				.isNotNull();
		assertThat(actual)
				.isEqualTo(new MeterType(1, "hot water"));
	}

	@Test
	@DisplayName("getMeterTypeById should throw an exception if such counter id does not exist in the database")
	void getMeterTypeById_shouldThrowExceptionWhenNotFound() {
		assertThrows(MeterTypeNotFoundException.class,
				() -> meterTypeRepository.getMeterTypeById(4));
	}
}