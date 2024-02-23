package ru.patseev.auditstarter.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.auditstarter.domain.UserAction;
import ru.patseev.auditstarter.dto.UserActionDto;
import ru.patseev.auditstarter.manager.enums.ActionEnum;
import ru.patseev.auditstarter.repository.AuditRepository;
import ru.patseev.auditstarter.service.AuditService;
import ru.patseev.auditstarter.service.mapper.AuditMapper;

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

		if (userAction.isEmpty()) {
			return null;
		}

		return userAction
				.stream()
				.map(auditMapper::toDto)
				.toList();
	}
}