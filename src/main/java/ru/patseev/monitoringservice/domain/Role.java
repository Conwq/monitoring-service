package ru.patseev.monitoringservice.domain;

import java.util.Objects;

/**
 * The Role class represents a user role in the system.
 */
public class Role {

	/**
	 * The unique identifier for the role.
	 */
	private Integer roleId;

	/**
	 * The name of the role.
	 */
	private String roleName;

	/**
	 * Constructs an empty Role object.
	 */
	public Role() {
	}

	/**
	 * Constructs a Role object with the specified parameters.
	 *
	 * @param roleId   The unique identifier for the role
	 * @param roleName The name of the role
	 */
	public Role(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	/**
	 * Retrieves the unique identifier for the role.
	 *
	 * @return The roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Sets the unique identifier for the role.
	 *
	 * @param roleId The roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * Retrieves the name of the role.
	 *
	 * @return The roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the name of the role.
	 *
	 * @param roleName The roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		Role role = (Role) o;
		return Objects.equals(roleId, role.roleId) &&
				Objects.equals(roleName, role.roleName);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(roleId, roleName);
	}
}
