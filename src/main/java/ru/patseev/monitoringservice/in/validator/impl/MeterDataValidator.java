package ru.patseev.monitoringservice.in.validator.impl;

import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * Validator implementation for validating DataMeterDto objects.
 */
public class MeterDataValidator implements Validator<DataMeterDto> {

	/**
	 * Validates the provided DataMeterDto object.
	 *
	 * @param dataMeterDto The DataMeterDto object to validate.
	 * @return true if the DataMeterDto object is valid, false otherwise.
	 */
	@Override
	public boolean validate(DataMeterDto dataMeterDto) {
		return dataMeterDto == null || dataMeterDto.value() <= 0;
	}
}
