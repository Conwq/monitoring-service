package ru.patseev.monitoringservice.data_meter_service.repository;

import ru.patseev.monitoringservice.data_meter_service.domain.MeterType;

import java.util.List;

/**
 * The MeterTypeRepository interface provides methods for interacting with meter type data storage.
 */
public interface MeterTypeRepository {

	/**
	 * Retrieves a list of all meter types.
	 *
	 * @return A list of MeterType representing all available meter types.
	 */
	List<MeterType> findAllMeterType();

	/**
	 * Saves a new type of meter.
	 *
	 * @param meterType New type of meter to be saved.
	 */
	void saveMeterType(MeterType meterType);
}