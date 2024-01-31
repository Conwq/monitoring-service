package ru.patseev.monitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.patseev.monitoringservice.enums.ActionEnum;

import java.sql.Timestamp;

/**
 * Represents an action performed by a user, containing information such as the timestamp of the action and the type of action.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAction {

	/**
	 * The unique identifier for the action.
	 */
	private Integer actionId;

	/**
	 * The timestamp of the action.
	 */
	private Timestamp actionAt;

	/**
	 * The type of action performed by the user (e.g., CREATE, UPDATE, DELETE).
	 */
	private ActionEnum action;

	/**
	 * The user ID associated with the action.
	 */
	private Integer userId;
}