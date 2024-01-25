package ru.patseev.monitoringservice.data_meter_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.db.DataMeterDatabase;
import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DataMeterRepositoryImpl implements DataMeterRepository {
	private final DataMeterDatabase dataMetersDatabase;

	@Override
	public Optional<DataMeter> findLastDataMeter(String username) {
		return dataMetersDatabase.getLastMeterData(username);
	}

	@Override
	public void saveDataMeter(String username, DataMeter dataMeter) {
		dataMetersDatabase.putData(username, dataMeter);
	}

	@Override
	public Optional<DataMeter> getMeterDataForSpecifiedMonth(String username, int month) {
		return dataMetersDatabase.getMetersDataByMonth(username, month);
	}

	@Override
	public List<DataMeter> getAllMeterData(String username) {
		return dataMetersDatabase.getMeterData(username);
	}
}