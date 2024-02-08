package ru.patseev.monitoringservice.in.validator.impl;

import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * The UserDtoValidator class provides validation for UserDto objects.
 */
public class UserDtoValidator implements Validator<UserDto> {

	/**
	 * Validates the given UserDto object.
	 *
	 * @param userDto The UserDto object to validate.
	 * @return true if the UserDto object is valid, false otherwise.
	 */
	@Override
	public boolean validate(UserDto userDto) {
		String username = userDto.username();
		String password = userDto.password();

		return username.length() > 0 &&
				!username.isBlank() &&
				password.length() > 0 &&
				!password.isBlank();
	}
}
