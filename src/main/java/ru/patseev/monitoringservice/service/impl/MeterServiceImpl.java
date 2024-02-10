package ru.patseev.monitoringservice.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.exception.MeterDataWasSubmittedException;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.service.MeterService;
import ru.patseev.monitoringservice.service.mapper.MeterDataMapper;
import ru.patseev.monitoringservice.service.mapper.MeterTypeMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The MeterServiceImpl class is an implementation of the DataMeterService interface.
 */
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

	/**
	 * The repository responsible for data meter operations.
	 */
	private final DataMeterRepository dataMeterRepository;

	/**
	 * The repository responsible for meter type operations.
	 */
	private final MeterTypeRepository meterTypeRepository;

	/**
	 * The MeterTypeMapper is responsible for mapping between MeterType entities and MeterTypeDto DTOs.
	 */
	private final MeterTypeMapper meterTypeMapper;

	/**
	 * The MeterDataMapper is responsible for mapping between DataMeter entities and DataMeterDto DTOs.
	 */
	private final MeterDataMapper meterDataMapper;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataMeterDto getCurrentDataMeter(int userId) {
		return dataMeterRepository.findLastDataMeter(userId)
				.map(dataMeter -> {
					MeterType meterType = meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId());
					return meterDataMapper.toDto(dataMeter, meterType);
				})
				.orElseThrow(() -> new DataMeterNotFoundException("Data meter not found"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(int userId, DataMeterDto dataMeterDto) {
		boolean meterDataSubmitted = dataMeterRepository.checkMeterDataForCurrentMonth(userId, dataMeterDto.meterTypeId());
		System.out.println(meterDataSubmitted);
		if (meterDataSubmitted) {
			throw new MeterDataWasSubmittedException("Meter data submitted for the current month");
		}

		DataMeter dataMeter = meterDataMapper.toEntity(userId, dataMeterDto);
		dataMeterRepository.saveDataMeter(dataMeter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeterDto> getMeterDataForSpecifiedMonth(int userId, int month) {
		return dataMeterRepository
				.getMeterDataForSpecifiedMonth(userId, month)
				.stream()
				.map(dataMeter -> {
					MeterType meterType = meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId());
					return meterDataMapper.toDto(dataMeter, meterType);
				})
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeterDto> getUserMeterData(int userId) {
		List<DataMeter> allMeterData = dataMeterRepository.getAllMeterData(userId);

		if (allMeterData.isEmpty()) {
			return Collections.emptyList();
		}

		return allMeterData.stream()
				.map(dataMeter -> {
					MeterType meterType = meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId());
					return meterDataMapper.toDto(dataMeter, meterType);
				})
				.toList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<DataMeterDto>> getDataFromAllMeterUsers() {
		Map<Integer, List<DataMeter>> allMeterData = dataMeterRepository.getDataFromAllMeterUsers();

		if (allMeterData.isEmpty()) {
			return Collections.emptyMap();
		}

		return allMeterData
				.entrySet()
				.stream()
				.collect(Collectors.toMap(integerListEntry -> String.valueOf(integerListEntry.getKey()),
						entry -> entry
								.getValue()
								.stream()
								.map(dataMeter -> {
									MeterType meterType = meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId());
									return meterDataMapper.toDto(dataMeter, meterType);
								})
								.collect(Collectors.toList())
				));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MeterTypeDto> getAvailableMeterType() {
		return meterTypeRepository.findAllMeterType()
				.stream()
				.map(meterTypeMapper::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMeterType(MeterTypeDto meterTypeDto) {
		MeterType meterType = meterTypeMapper.toEntity(meterTypeDto);
		meterTypeRepository.saveMeterType(meterType);
	}
}