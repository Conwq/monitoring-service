package ru.patseev.monitoringservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.patseev.monitoringservice.enums.RoleEnum;

/**
 * The UserDto record represents a Data Transfer Object (DTO) for user-related information.
 *
 * @param userId   The unique identifier for the user, or null if not applicable.
 * @param username The username of the user.
 * @param password The password of the user, or null if not applicable.
 * @param role     The role of the user represented by a RoleEnum, or null if not applicable.
 */
@JsonSerialize
public record UserDto(Integer userId,
					  @NotBlank(message = "Username must not be empty")
					  @Size(min = 6, max = 32, message = "Username must be at least 6 characters long")
					  String username,
					  @NotBlank(message = "Password must not be empty")
					  @Size(min = 3, max = 32, message = "Password must be at least 3 characters long")
					  String password,
					  RoleEnum role) {
}
