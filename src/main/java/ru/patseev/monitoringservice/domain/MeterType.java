package ru.patseev.monitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a type of meter with a unique identifier and a name.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterType {

	/**
	 * The unique identifier for the meter type.
	 */
	private Integer meterTypeId;

	/**
	 * The name of the meter type.
	 */
	private String typeName;
}