package ru.patseev.monitoringservice.data_meter_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The DataMeter class represents metered data for a specific user on a particular date.
 * It includes information such as date, heating data, cold water data, hot water data, and the associated username.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataMeter {
	/**
	 * The date for which the metered data is recorded.
	 */
	private LocalDate date;

	/**
	 * The metered data for heating.
	 */
	private Long heatingData;

	/**
	 * The metered data for cold water.
	 */
	private Long coldWaterData;

	/**
	 * The metered data for hot water.
	 */
	private Long hotWaterData;

	/**
	 * The username associated with the metered data.
	 */
	private String username;
}