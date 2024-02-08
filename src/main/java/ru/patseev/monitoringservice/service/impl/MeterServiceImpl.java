package ru.patseev.monitoringservice.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.service.MeterService;

import java.sql.Timestamp;
import java.time.Instant;
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
	 * {@inheritDoc}
	 */
	@Override
	public DataMeterDto getCurrentDataMeter(int userId) {
		return dataMeterRepository.findLastDataMeter(userId)
				.map(this::toDto)
				.orElseThrow(() -> new DataMeterNotFoundException("Данные счетчика не найдены."));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(int userId, DataMeterDto dataMeterDto) {
		DataMeter dataMeter = toEntity(userId, dataMeterDto);
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
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeterDto> getAllMeterData(int userId) {
		List<DataMeter> allMeterData = dataMeterRepository.getAllMeterData(userId);

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
		Map<Integer, List<DataMeter>> allMeterData = dataMeterRepository.getDataFromAllMeterUsers();

		if (allMeterData.isEmpty()) {
			return Collections.emptyMap();
		}

		return allMeterData
				.entrySet()
				.stream()
				.collect(Collectors.toMap(integerListEntry -> String.valueOf(integerListEntry.getKey()),
						entry -> entry.getValue().stream().map(this::toDto).collect(Collectors.toList())
				));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MeterTypeDto> getAvailableMeterType() {
		return meterTypeRepository.findAllMeterType()
				.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMeterType(MeterTypeDto meterTypeDto) {
		MeterType meterType = toEntity(meterTypeDto);
		meterTypeRepository.saveMeterType(meterType);
	}

	/**
	 * Converts a MeterTypeDto object to a corresponding MeterType entity.
	 *
	 * @param meterTypeDto The MeterTypeDto object to be converted.
	 * @return The MeterType entity.
	 */
	private MeterType toEntity(MeterTypeDto meterTypeDto) {
		return new MeterType(meterTypeDto.meterTypeId(), meterTypeDto.typeName());
	}

	/**
	 * Converts a MeterType entity to a corresponding MeterTypeDto object.
	 *
	 * @param meterType The MeterType entity to be converted.
	 * @return The MeterTypeDto object.
	 */
	private MeterTypeDto toDto(MeterType meterType) {
		return new MeterTypeDto(meterType.getMeterTypeId(), meterType.getTypeName());
	}

	/**
	 * Converts a DataMeter entity to a corresponding DataMeterDto object.
	 *
	 * @param dataMeter The DataMeter entity to be converted.
	 * @return The DataMeterDto object.
	 */
	private DataMeterDto toDto(DataMeter dataMeter) {
		MeterType meterType = meterTypeRepository.getMeterTypeById(dataMeter.getMeterTypeId());

		return new DataMeterDto(
				dataMeter.getSubmissionDate(),
				dataMeter.getValue(),
				meterType.getMeterTypeId(),
				meterType.getTypeName()
		);
	}

	/**
	 * Converts a DataMeterDto object to a corresponding DataMeter entity.
	 *
	 * @param dto The DataMeterDto to be converted.
	 * @return The DataMeter entity.
	 */
	private DataMeter toEntity(int userId, DataMeterDto dto) {
		return DataMeter.builder()
				.submissionDate(Timestamp.from(Instant.now()))
				.value(dto.value())
				.meterTypeId(dto.meterTypeId())
				.userId(userId)
				.build();
	}
}