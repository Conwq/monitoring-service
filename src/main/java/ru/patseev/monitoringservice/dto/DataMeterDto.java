package ru.patseev.monitoringservice.dto;

import java.sql.Timestamp;

/**
 * The DataMeterDto record represents data meter readings in a transferable format.
 *
 * @param date           The date for which the metered data is recorded.
 * @param value          The value of the metered data.
 * @param meterTypeId    The ID of the meter type associated with the data.
 * @param meterTypeName  The name of the meter type associated with the data.
 */
public record DataMeterDto(Timestamp date,
						   Long value,
						   Integer meterTypeId,
						   String meterTypeName) {
}