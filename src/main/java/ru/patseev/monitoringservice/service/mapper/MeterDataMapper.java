package ru.patseev.monitoringservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.DataMeterDto;

/**
 * The MeterDataMapper interface provides methods for mapping between DataMeter and DataMeterDto objects.
 */
@Mapper(componentModel = "spring")
public interface MeterDataMapper {

	/**
	 * Maps the given userId and DataMeterDto to a DataMeter entity.
	 *
	 * @param userId       The user ID associated with the meter data.
	 * @param dataMeterDto The DataMeterDto object containing meter data information.
	 * @return The DataMeter entity.
	 */
	@Mappings({
			@Mapping(target = "userId", source = "userId"),
			@Mapping(target = "submissionDate", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))"),
			@Mapping(target = "meterDataId", ignore = true)
	})
	DataMeter toEntity(int userId, DataMeterDto dataMeterDto);


	/**
	 * Maps the given DataMeter and MeterType objects to a DataMeterDto.
	 *
	 * @param dataMeter The DataMeter object containing meter data.
	 * @param meterType The MeterType object containing meter type information.
	 * @return The DataMeterDto object.
	 */
	@Mappings({
			@Mapping(target = "meterTypeName", source = "meterType.typeName"),
			@Mapping(target = "meterTypeId", source = "meterType.meterTypeId")
	})
	DataMeterDto toDto(DataMeter dataMeter, MeterType meterType);
}
