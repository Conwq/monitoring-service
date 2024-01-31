package ru.patseev.monitoringservice.dto;

import java.time.LocalDate;

/**
 * The DataMeterDto record represents data meter readings in a transferable format.
 *
 * @param date           The date for which the metered data is recorded.
 * @param value          The value of the metered data.
 * @param meterTypeId    The ID of the meter type associated with the data.
 * @param meterTypeName  The name of the meter type associated with the data.
 */
public record DataMeterDto(LocalDate date, Long value, Integer meterTypeId, String meterTypeName) {
}