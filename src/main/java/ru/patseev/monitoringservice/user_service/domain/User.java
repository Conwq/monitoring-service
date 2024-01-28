package ru.patseev.monitoringservice.user_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The User class represents a user entity in the monitoring application.
 * It includes information such as the username, password, and role of the user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/**
	 * The username of the user.
	 */
	private String username;

	/**
	 * The password associated with the user's account.
	 */
	private String password;

	/**
	 * The role of the user, indicating their level of access and permissions.
	 */
	private Role role;
}