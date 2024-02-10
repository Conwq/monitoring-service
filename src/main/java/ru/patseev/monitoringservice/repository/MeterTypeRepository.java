package ru.patseev.monitoringservice.repository;

import ru.patseev.monitoringservice.domain.MeterType;

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

	/**
	 * Retrieves a meter type by its ID.
	 *
	 * @param meterTypeId The ID of the meter type to retrieve.
	 * @return The MeterType object representing the meter type with the specified ID,or null if not found.
	 */
	MeterType getMeterTypeById(int meterTypeId);

	/**
	 * Checks the existence of a meter type with the specified type name.
	 *
	 * @param typeName the name of the meter type to check for existence
	 * @return true if a meter type with the specified type name exists; false otherwise
	 */
	boolean checkMeterTypeExistence(String typeName);
}