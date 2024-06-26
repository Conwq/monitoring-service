package ru.patseev.monitoringservice.dto;

import ru.patseev.monitoringservice.enums.ActionEnum;

import java.sql.Timestamp;

/**
 * Represents a dto for user actions, containing information such as the timestamp of the action and the type of action.
 *
 * @param actionAt The timestamp of the action.
 * @param action   The type of action performed by the user.
 */
public record UserActionDto(Timestamp actionAt,
							ActionEnum action) {
}