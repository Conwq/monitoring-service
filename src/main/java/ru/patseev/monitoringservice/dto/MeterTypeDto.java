package ru.patseev.monitoringservice.dto;

/**
 * The MeterTypeDto record represents a meter type in a transferable format.
 *
 * @param meterTypeId The ID of the meter type.
 * @param typeName    The name of the meter type.
 */
public record MeterTypeDto(Integer meterTypeId,
						   String typeName) {
}