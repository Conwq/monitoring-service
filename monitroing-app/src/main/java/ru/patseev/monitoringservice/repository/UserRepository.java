package ru.patseev.monitoringservice.repository;

import ru.patseev.monitoringservice.domain.User;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for interacting with user data storage.
 */
public interface UserRepository {

	/**
	 * Saves a user to the repository.
	 *
	 * @param user The User object representing the user to be saved.
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

	/**
	 * Checks if a user with the specified username exists in the repository.
	 *
	 * @param username The username to check for existence.
	 * @return true if the user exists, otherwise false.
	 */
	boolean existUserByUsername(String username);
}