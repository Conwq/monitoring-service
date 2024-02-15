package ru.patseev.monitoringservice.service.mapper;

import org.mapstruct.Mapper;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.dto.MeterTypeDto;

/**
 * Mapper interface for converting between MeterTypeDto and MeterType entities.
 */
@Mapper(componentModel = "spring")
public interface MeterTypeMapper {

	/**
	 * Converts a MeterTypeDto to a MeterType entity.
	 *
	 * @param meterTypeDto The MeterTypeDto to be converted.
	 * @return The corresponding MeterType entity.
	 */
	MeterType toEntity(MeterTypeDto meterTypeDto);

	/**
	 * Converts a MeterType entity to a MeterTypeDto.
	 *
	 * @param meterType The MeterType entity to be converted.
	 * @return The corresponding MeterTypeDto.
	 */
	MeterTypeDto toDto(MeterType meterType);
}
