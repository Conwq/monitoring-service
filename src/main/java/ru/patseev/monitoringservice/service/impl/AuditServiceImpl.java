package ru.patseev.monitoringservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.patseev.monitoringservice.domain.UserAction;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.AuditRepository;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.mapper.AuditMapper;

import java.util.List;

/**
 * The AuditServiceImpl class is an implementation of the AuditService interface.
 */
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

	/**
	 * The audit repository for interacting with the audit data storage.
	 */
	private final AuditRepository auditRepository;

	/**
	 * The mapper for converting between ActionEnum objects and UserAction objects.
	 */
	private final AuditMapper auditMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUserAction(ActionEnum action, int userId) {
		UserAction userAction = auditMapper.toEntity(action, userId);
		auditRepository.save(userAction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserActionDto> getUserActions(int userId) {
		List<UserAction> userAction = auditRepository
				.findUserActionsByUserId(userId);

		if (userAction == null) {
			throw new UserNotFoundException("User is not found");
		}

		return userAction
				.stream()
				.map(auditMapper::toDto)
				.toList();
	}
}