package ru.patseev.monitoringservice.user_service.service;

import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

/**
 * The UserService interface defines methods for user-related operations.
 */
public interface UserService {

	/**
	 * Saves a user based on the provided UserDto.
	 *
	 * @param userDto The UserDto object representing the user to be saved.
	 * @return The saved UserDto object.
	 * @throws UserAlreadyExistException Thrown if such a user is already registered.
	 */
	UserDto saveUser(UserDto userDto) throws UserAlreadyExistException;

	/**
	 * Authenticates a user based on the provided UserDto.
	 *
	 * @param userDto The UserDto object representing the user to be authenticated.
	 * @return The authenticated UserDto.
	 * @throws UserNotFoundException Thrown if the user with the transferred data is not found.
	 */
	UserDto authUser(UserDto userDto) throws UserNotFoundException;

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username.
	 */
	UserDto getUser(String username);
}