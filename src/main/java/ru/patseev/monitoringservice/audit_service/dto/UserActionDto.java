package ru.patseev.monitoringservice.audit_service.dto;

import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;

import java.time.LocalDateTime;

/**
 * Represents a dto for user actions, containing information such as the timestamp of the action and the type of action.
 *
 * @param actionAt The timestamp of the action.
 * @param action   The type of action performed by the user.
 */
public record UserActionDto(LocalDateTime actionAt, ActionEnum action) {
}