package ru.patseev.monitoringservice.in.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.patseev.monitoringservice.dto.DataMeterDto;

/**
 * The MeterDataValidator class is responsible for validating DataMeterDto objects.
 * It checks if the supplied value in the DataMeterDto object is within acceptable bounds.
 */
@Component
public class MeterDataValidator implements Validator {

	/**
	 * Determines if the validator supports the validation of the given class.
	 *
	 * @param clazz The class to be checked for support.
	 * @return True if the class is supported for validation, false otherwise.
	 */
	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return DataMeterDto.class.equals(clazz);
	}

	/**
	 * Validates the provided target object and collects errors in the provided Errors object.
	 * This method checks if the value in the DataMeterDto object is too small.
	 *
	 * @param target The object to be validated.
	 * @param errors The Errors object to collect validation errors.
	 */
	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		DataMeterDto meterType = (DataMeterDto) target;
		if (meterType.value() < 1) {
			errors.rejectValue("value", "value.tooSmall", "The supplied value is too small");
		}
	}
}
