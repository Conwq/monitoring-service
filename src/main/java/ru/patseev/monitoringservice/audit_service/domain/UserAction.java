package ru.patseev.monitoringservice.audit_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAction {
	private LocalDateTime actionAt;
	private ActionEnum action;
}
