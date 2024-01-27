package ru.patseev.monitoringservice.audit_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.db.AuditDatabase;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;

import java.util.List;

/**
 * The AuditRepositoryImpl class is an implementation of the AuditRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {
	private final AuditDatabase auditDatabase;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(String username, UserAction userAction) {
		auditDatabase.saveUserAction(username, userAction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAction> findUserActionByUsername(String username) {
		return auditDatabase.getUserActions(username);
	}
}