package ru.patseev.monitoringservice.user_service.service;

import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The UserService interface defines methods for user-related operations.
 */
public interface UserService {

	/**
	 * Saves a user based on the provided UserDto.
	 *
	 * @param userDto The UserDto object representing the user to be saved.
	 */
	void saveUser(UserDto userDto);

	/**
	 * Authenticates a user based on the provided UserDto.
	 *
	 * @param userDto The UserDto object representing the user to be authenticated.
	 * @return The authenticated UserDto.
	 */
	UserDto authUser(UserDto userDto);
}