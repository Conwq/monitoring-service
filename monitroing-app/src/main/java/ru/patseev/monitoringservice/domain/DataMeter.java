package ru.patseev.monitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * The DataMeter class represents metered data for a specific user on a particular submissionDate.
 * It includes information such as submissionDate, heating data, cold water data, hot water data, and the associated username.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataMeter {

	/**
	 * The unique identifier for the recorded meter data.
	 */
	private Integer meterDataId;

	/**
	 * The submissionDate for which the metered data is recorded.
	 */
	private Timestamp submissionDate;

	/**
	 * The metered value for the specified submissionDate.
	 */
	private Long value;

	/**
	 * The type of the meter associated with the recorded data.
	 */
	private Integer meterTypeId;

	/**
	 * The user ID associated with the recorded meter data.
	 */
	private Integer userId;
}
