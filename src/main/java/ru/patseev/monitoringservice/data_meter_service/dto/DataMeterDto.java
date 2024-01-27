package ru.patseev.monitoringservice.data_meter_service.dto;

import java.time.LocalDate;

/**
 * The DataMeterDto record represents a dto for metered data.
 * It includes information such as date, heating data, cold water data, and hot water data.
 *
 * @param date          The date for which the metered data is recorded.
 * @param heatingData   The metered data for heating.
 * @param coldWaterData The metered data for cold water.
 * @param hotWaterData  The metered data for hot water.
 */
public record DataMeterDto(LocalDate date, Long heatingData, Long coldWaterData, Long hotWaterData) {
}