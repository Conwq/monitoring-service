package ru.patseev.monitoringservice.user_service.repository;

import ru.patseev.monitoringservice.user_service.domain.User;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for interacting with user data storage.
 */
public interface UserRepository {

	/**
	 * Saves a user to the repository.
	 *
	 * @param user The {@code User} object representing the user to be saved.
	 * @return The unique identifier (id) assigned to the saved user.
	 */
	Integer saveUser(User user);

	/**
	 * Finds a user by their username in the repository.
	 *
	 * @param username The username of the user to find.
	 * @return An Optional containing the found user, or empty if not found.
	 */
	Optional<User> findUserByUsername(String username);
}