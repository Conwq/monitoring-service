package ru.patseev.monitoringservice.user_service.dto;

import jakarta.annotation.Nullable;

/**
 * The UserDto record represents a Data Transfer Object (DTO) for user-related information.
 *
 * @param userId   The unique identifier for the user, or null if not applicable.
 * @param username The username of the user.
 * @param password The password of the user, or null if not applicable.
 * @param role     The role of the user represented by a RoleEnum, or null if not applicable.
 */
public record UserDto(@Nullable Integer userId, String username, String password, @Nullable RoleEnum role) {
}
