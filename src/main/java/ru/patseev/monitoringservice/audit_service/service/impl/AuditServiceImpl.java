package ru.patseev.monitoringservice.audit_service.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

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

	@Override
	public List<UserActionDto> getUserAction(String username) {
		List<UserAction> userAction = auditRepository
				.findUserActionByUsername(username);

		if (userAction == null) {
			throw new UserNotFoundException("\nПользователь не найден.");
		}

		return userAction
				.stream()
				.map(this::toDto)
				.toList();
	}

	private UserActionDto toDto(UserAction userAction) {
		return new UserActionDto(userAction.getActionAt(), userAction.getAction());
	}
}