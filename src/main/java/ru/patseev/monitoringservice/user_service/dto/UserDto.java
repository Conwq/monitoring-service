package ru.patseev.monitoringservice.user_service.dto;

import jakarta.annotation.Nullable;
import ru.patseev.monitoringservice.user_service.domain.Role;

public record UserDto(String username, String password, @Nullable Role role) {
}
