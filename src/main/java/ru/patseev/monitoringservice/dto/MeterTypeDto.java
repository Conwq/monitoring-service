package ru.patseev.monitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The MeterTypeDto record represents a meter type in a transferable format.
 *
 * @param meterTypeId The ID of the meter type.
 * @param typeName    The name of the meter type.
 */
public record MeterTypeDto(Integer meterTypeId,
						   @NotBlank
						   @Size(min = 4, max = 20)
						   String typeName) {
}