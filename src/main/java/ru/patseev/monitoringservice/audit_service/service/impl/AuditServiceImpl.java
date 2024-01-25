package ru.patseev.monitoringservice.audit_service.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
	private final AuditRepository auditRepository;

	@Override
	public void saveUserAction(ActionEnum action, UserDto userDto) {
		UserAction userAction = UserAction.builder()
				.actionAt(LocalDateTime.now())
				.action(action)
				.build();

		auditRepository.save(userDto.username(), userAction);
	}
}