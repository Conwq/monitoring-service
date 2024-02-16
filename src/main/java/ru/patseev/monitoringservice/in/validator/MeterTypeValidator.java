package ru.patseev.monitoringservice.in.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.patseev.monitoringservice.dto.MeterTypeDto;

/**
 * Validator for objects of type {@link ru.patseev.monitoringservice.dto.MeterTypeDto}.
 * It checks that the typeName field is not empty and has sufficient length.
 */
@Component
public class MeterTypeValidator implements Validator {

	/**
	 * Checks if the validator supports validation for the given class.
	 *
	 * @param clazz the class of the object to be validated
	 * @return true if the object's class is MeterTypeDto, otherwise false
	 */
	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return MeterTypeDto.class.equals(clazz);
	}

	/**
	 * Performs validation on the given object and adds errors to the Errors object in case of mismatch.
	 *
	 * @param target the object to be validated
	 * @param errors the object to store validation errors
	 */
	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeName", "typeName.empty", "Type name can not be empty");
		MeterTypeDto meterType = (MeterTypeDto) target;
		if (meterType.typeName().length() < 4) {
			errors.rejectValue("typeName", "typeName.tooShort", "Type name must be at least 4 characters long");
		}
	}
}
