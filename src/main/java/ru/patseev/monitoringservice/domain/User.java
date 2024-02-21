package ru.patseev.monitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The User class represents a user entity in the monitoring application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/**
	 * The unique identifier for the user.
	 */
	private Integer userId;

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
	private Integer roleId;
}
