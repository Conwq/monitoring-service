package ru.patseev.monitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Role class represents a user role in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	/**
	 * The unique identifier for the role.
	 */
	private Integer roleId;

	/**
	 * The name of the role.
	 */
	private String roleName;
}
