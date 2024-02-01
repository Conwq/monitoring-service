package ru.patseev.monitoringservice.user_service.dto;

import jakarta.annotation.Nullable;
import ru.patseev.monitoringservice.user_service.domain.Role;

/**
 * The code UserDto record represents a dto for user-related information.
 * It includes the username, password, and an optional role.

 * @param username The username associated with the user.
 * @param password The password associated with the user.
 * @param role     The role of the user, which can be {@code null} if not applicable.
 */
public record UserDto(String username, String password, @Nullable Role role) {
}