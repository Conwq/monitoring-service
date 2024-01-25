package ru.patseev.monitoringservice.user_service.db;

import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The UserDatabase class represents an in-memory database for storing user information.
 */
public class UserDatabase {

	/**
	 * A map to store user data with unique identifiers.
	 */
	private final Map<Integer, User> usersData = new HashMap<>();

	/**
	 * An identifier to generate unique user IDs.
	 */
	private static int ID = 0;

	/**
	 * Constructs a new UserDatabase and initializes it with an admin user.
	 */
	public UserDatabase() {
		User admin = User.builder()
				.username("admin")
				.password("admin")
				.role(Role.ADMIN)
				.build();

		usersData.put(++ID, admin);
	}

	/**
	 * Saves a user to the database with a unique identifier.
	 *
	 * @param user The User object representing the user to be saved.
	 */
	public void saveUser(User user) {
		usersData.put(++ID, user);
	}

	/**
	 * Finds a user by their username in the database.
	 *
	 * @param username The username of the user to find.
	 * @return An Optional containing the found user, or empty if not found.
	 */
	public Optional<User> findUserByUsername(String username) {
		return usersData
				.values()
				.stream()
				.filter(user -> user.getUsername().equals(username))
				.findAny();
	}
}