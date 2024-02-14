package ru.patseev.monitoringservice.in.validator.impl;

import org.springframework.stereotype.Component;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * Validator implementation for validating MeterTypeDto objects.
 */
@Component
public class MeterTypeValidator implements Validator<MeterTypeDto> {

	/**
	 * Validates the provided MeterTypeDto object.
	 *
	 * @param meterTypeDto The MeterTypeDto object to validate.
	 * @return true if the MeterTypeDto object is valid, false otherwise.
	 */
	@Override
	public boolean validate(MeterTypeDto meterTypeDto) {
		return meterTypeDto == null ||
				meterTypeDto.typeName() == null ||
				meterTypeDto.typeName().trim().length() <= 4;
	}
}
