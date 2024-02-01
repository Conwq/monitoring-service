package ru.patseev.monitoringservice.audit_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;

import java.time.LocalDateTime;

/**
 * Represents an action performed by a user, containing information such as the timestamp of the action and the type of action.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAction {
	/**
	 * The timestamp of the action.
	 */
	private LocalDateTime actionAt;

	/**
	 * The type of action performed by the user.
	 */
	private ActionEnum action;
}