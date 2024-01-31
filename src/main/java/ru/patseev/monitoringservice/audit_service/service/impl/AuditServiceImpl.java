package ru.patseev.monitoringservice.audit_service.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * The AuditServiceImpl class is an implementation of the AuditService interface.
 */
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

	/**
	 * The audit repository for interacting with the audit data storage.
	 */
	private final AuditRepository auditRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUserAction(ActionEnum action, UserDto userDto) {
		UserAction userAction = UserAction.builder()
				.actionAt(Timestamp.from(Instant.now()))
				.action(action)
				.build();

		auditRepository.save(userDto.userId(), userAction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserActionDto> getUserAction(int userId) {
		List<UserAction> userAction = auditRepository
				.findUserActionByUserId(userId);

		if (userAction == null) {
			throw new UserNotFoundException("\nПользователь не найден.");
		}

		return userAction
				.stream()
				.map(this::toDto)
				.toList();
	}

	/**
	 * Converts a UserAction object to a UserActionDto.
	 *
	 * @param userAction The UserAction object to be converted.
	 * @return A UserActionDto representing the converted data.
	 */
	private UserActionDto toDto(UserAction userAction) {
		return new UserActionDto(userAction.getActionAt().toLocalDateTime(), userAction.getAction());
	}
}