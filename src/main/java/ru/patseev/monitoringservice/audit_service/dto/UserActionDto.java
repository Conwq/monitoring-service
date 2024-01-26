package ru.patseev.monitoringservice.audit_service.dto;

import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;

import java.time.LocalDateTime;

public record UserActionDto(LocalDateTime actionAt, ActionEnum action) {
}
