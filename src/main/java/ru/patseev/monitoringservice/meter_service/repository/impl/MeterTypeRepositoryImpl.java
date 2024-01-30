package ru.patseev.monitoringservice.meter_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.meter_service.db.MeterTypeDatabase;
import ru.patseev.monitoringservice.meter_service.domain.MeterType;
import ru.patseev.monitoringservice.meter_service.repository.MeterTypeRepository;

import java.util.List;

/**
 * The MeterTypeRepositoryImpl class is an implementation of the MeterTypeRepository interface.
 * It provides methods for interacting with meter type data storage.
 */
@RequiredArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

	/**
	 * The database responsible for storing meter type information.
	 */
	private final MeterTypeDatabase meterTypeDatabase;

	/**
	 * Retrieves a list of all meter types.
	 *
	 * @return A list of MeterType representing all available meter types.
	 */
	@Override
	public List<MeterType> findAllMeterType() {
		return meterTypeDatabase.getAllMeterType();
	}

	/**
	 * Saves a new type of meter.
	 *
	 * @param meterType New type of meter to be saved.
	 */
	@Override
	public void saveMeterType(MeterType meterType) {
		meterTypeDatabase.putMeterType(meterType);
	}
}