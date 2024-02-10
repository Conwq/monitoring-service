package ru.patseev.monitoringservice.domain;

import java.util.Objects;

/**
 * The User class represents a user entity in the monitoring application.
 */
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

	/**
	 * Constructs an empty User object.
	 */
	public User() {
	}

	/**
	 * Constructs a User object with the specified parameters.
	 *
	 * @param userId   The unique identifier for the user
	 * @param username The username of the user
	 * @param password The password associated with the user's account
	 * @param roleId   The role of the user
	 */
	public User(Integer userId, String username, String password, Integer roleId) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	/**
	 * Retrieves the unique identifier for the user.
	 *
	 * @return The userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the unique identifier for the user.
	 *
	 * @param userId The userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Retrieves the username of the user.
	 *
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param username The username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the password associated with the user's account.
	 *
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password associated with the user's account.
	 *
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retrieves the role of the user.
	 *
	 * @return The roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role of the user.
	 *
	 * @param roleId The roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param o The reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId) &&
				Objects.equals(username, user.username) &&
				Objects.equals(password, user.password) &&
				Objects.equals(roleId, user.roleId);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(userId, username, password, roleId);
	}
}
