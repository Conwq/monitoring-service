package ru.patseev.monitoringservice.data_meter_service.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.domain.DataMeter;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.data_meter_service.exception.MeterDataFeedConflictException;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;
import ru.patseev.monitoringservice.data_meter_service.service.DataMeterService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The DataMeterServiceImpl class is an implementation of the DataMeterService interface.
 */
@RequiredArgsConstructor
public class DataMeterServiceImpl implements DataMeterService {
	private final DataMeterRepository dataMeterRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataMeterDto getCurrentDataMeter(UserDto userDto) {
		return dataMeterRepository.findLastDataMeter(userDto.username())
				.map(this::toDto)
				.orElseThrow(() -> new DataMeterNotFoundException("Данные счетчика не найдены."));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(UserDto userDto, DataMeterDto dataMeterDto) {
		int month = dataMeterDto.date().getMonth().getValue();

		if (dataMeterRepository.getMeterDataForSpecifiedMonth(userDto.username(), month).isPresent()) {
			throw new MeterDataFeedConflictException("Данные уже поданы в текущем месяце.");
		}

		DataMeter dataMeter = toEntity(dataMeterDto, userDto.username());
		dataMeterRepository.saveDataMeter(userDto.username(), dataMeter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataMeterDto getMeterDataForSpecifiedMonth(UserDto userDto, int month) {
		return dataMeterRepository
				.getMeterDataForSpecifiedMonth(userDto.username(), month)
				.map(this::toDto)
				.orElseThrow(() -> new DataMeterNotFoundException("Данные счетчика не найдены."));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeterDto> getAllMeterData(UserDto userDto) {
		List<DataMeter> allMeterData = dataMeterRepository.getAllMeterData(userDto.username());

		if (allMeterData.isEmpty()) {
			return Collections.emptyList();
		}

		return allMeterData.stream()
				.map(this::toDto)
				.toList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DataMeterDto>> getDataFromAllMeterUsers() {
		Map<String, List<DataMeter>> allMeterData = dataMeterRepository.getDataFromAllMeterUsers();

		if (allMeterData.isEmpty()) {
			return Collections.emptyMap();
		}

		return allMeterData
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> entry.getValue().stream().map(this::toDto).collect(Collectors.toList())
				));
	}

	/*
	 * Maps an entity to a dto.
	 */
	private DataMeterDto toDto(DataMeter dataMeter) {
		return new DataMeterDto(
				dataMeter.getDate(),
				dataMeter.getHeatingData(),
				dataMeter.getColdWaterData(),
				dataMeter.getHotWaterData()
		);
	}

	/*
	 * Maps a dto to an entity.
	 */
	private DataMeter toEntity(DataMeterDto dto, String username) {
		return DataMeter.builder()
				.date(dto.date())
				.heatingData(dto.heatingData())
				.coldWaterData(dto.coldWaterData())
				.hotWaterData(dto.hotWaterData())
				.username(username)
				.build();
	}
}