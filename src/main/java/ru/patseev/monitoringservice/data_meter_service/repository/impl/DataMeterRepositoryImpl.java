package ru.patseev.monitoringservice.data_meter_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.db.DataMeterDatabase;
import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The DataMeterRepositoryImpl class is an implementation of the DataMeterRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class DataMeterRepositoryImpl implements DataMeterRepository {
	private final DataMeterDatabase dataMetersDatabase;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DataMeter> findLastDataMeter(String username) {
		return dataMetersDatabase.getLastMeterData(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(String username, DataMeter dataMeter) {
		dataMetersDatabase.putData(username, dataMeter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DataMeter> getMeterDataForSpecifiedMonth(String username, int month) {
		return dataMetersDatabase.getMetersDataByMonth(username, month);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeter> getAllMeterData(String username) {
		return dataMetersDatabase.getMeterData(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DataMeter>> getDataFromAllMeterUsers() {
		return dataMetersDatabase.getAllMeterData();
	}
}