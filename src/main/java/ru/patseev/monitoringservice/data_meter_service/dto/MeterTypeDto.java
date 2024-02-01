package ru.patseev.monitoringservice.data_meter_service.dto;

import jakarta.annotation.Nullable;

/**
 * The MeterTypeDto record represents a meter type in a transferable format.
 *
 * @param meterTypeId  The ID of the meter type.
 * @param typeName     The name of the meter type.
 */
public record MeterTypeDto(@Nullable Integer meterTypeId,
						   String typeName) {
}