package ru.patseev.monitoringservice.in.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.patseev.monitoringservice.dto.UserDto;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return UserDto.class.equals(clazz);
	}

	@Override
	public void validate(@NonNull Object target,
						 @NonNull Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "Username must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Password must not be empty");
		UserDto userDto = (UserDto)target;
		if (userDto.username().length() < 6) {
			errors.rejectValue("username", "username.tooShort", "Username must be at least 6 characters long");
		}
		if (userDto.password().length() < 3) {
			errors.rejectValue("password", "password.tooShort", "Password must be at least 3 characters long");
		}
	}
}
