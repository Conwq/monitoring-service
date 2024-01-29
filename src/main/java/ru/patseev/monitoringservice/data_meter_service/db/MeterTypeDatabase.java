package ru.patseev.monitoringservice.data_meter_service.db;

import ru.patseev.monitoringservice.data_meter_service.domain.MeterType;

import java.util.ArrayList;
import java.util.List;

/**
 * The MeterTypeDatabase class represents an in-memory database for storing meter type information.
 */
public class MeterTypeDatabase {

	/**
	 * The unique identifier for meter types.
	 */
	private static int meterTypeId = 0;

	/**
	 * A list containing all available meter types.
	 */
	private final List<MeterType> meterTypes = new ArrayList<>(){{
		add(new MeterType(++meterTypeId, "Отопление"));
		add(new MeterType(++meterTypeId, "Холодная вода"));
		add(new MeterType(++meterTypeId, "Горячая вода"));
	}};

	/**
	 * Retrieves a list of all available meter types.
	 *
	 * @return A list of MeterType representing all available meter types.
	 */
	public List<MeterType> getAllMeterType() {
		return meterTypes;
	}

	/**
	 * Adds a new type meter.
	 *
	 * @param meterType  New type of meter.
	 */
	public void putMeterType(MeterType meterType) {
		meterType.setMeterTypeId(++meterTypeId);
		meterTypes.add(meterType);
	}
}