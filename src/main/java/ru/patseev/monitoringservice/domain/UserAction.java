package ru.patseev.monitoringservice.domain;

import ru.patseev.monitoringservice.enums.ActionEnum;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents an action performed by a user, containing information such as the timestamp of the action and the type of action.
 */
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

	/**
	 * Constructs an empty UserAction object.
	 */
	public UserAction() {
	}

	/**
	 * Constructs a UserAction object with the specified parameters.
	 *
	 * @param actionId The unique identifier for the action
	 * @param actionAt The timestamp of the action
	 * @param action   The type of action performed by the user
	 * @param userId   The user ID associated with the action
	 */
	public UserAction(Integer actionId, Timestamp actionAt, ActionEnum action, Integer userId) {
		this.actionId = actionId;
		this.actionAt = actionAt;
		this.action = action;
		this.userId = userId;
	}

	/**
	 * Retrieves the unique identifier for the action.
	 *
	 * @return The actionId
	 */
	public Integer getActionId() {
		return actionId;
	}

	/**
	 * Sets the unique identifier for the action.
	 *
	 * @param actionId The actionId to set
	 */
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	/**
	 * Retrieves the timestamp of the action.
	 *
	 * @return The actionAt
	 */
	public Timestamp getActionAt() {
		return actionAt;
	}

	/**
	 * Sets the timestamp of the action.
	 *
	 * @param actionAt The actionAt to set
	 */
	public void setActionAt(Timestamp actionAt) {
		this.actionAt = actionAt;
	}

	/**
	 * Retrieves the type of action performed by the user.
	 *
	 * @return The action
	 */
	public ActionEnum getAction() {
		return action;
	}

	/**
	 * Sets the type of action performed by the user.
	 *
	 * @param action The action to set
	 */
	public void setAction(ActionEnum action) {
		this.action = action;
	}

	/**
	 * Retrieves the user ID associated with the action.
	 *
	 * @return The userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user ID associated with the action.
	 *
	 * @param userId The userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
		UserAction that = (UserAction) o;
		return Objects.equals(actionId, that.actionId) &&
				Objects.equals(actionAt, that.actionAt) && action == that.action &&
				Objects.equals(userId, that.userId);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(actionId, actionAt, action, userId);
	}
}
