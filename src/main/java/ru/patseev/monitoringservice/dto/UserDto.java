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
					  @NotBlank
					  @Size(min = 6, max = 32)
					  String username,
					  @NotBlank
					  @Size(min = 3, max = 32)
					  String password,
					  RoleEnum role) {
}
