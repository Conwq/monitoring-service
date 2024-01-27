package ru.patseev.monitoringservice.audit_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.db.AuditDatabase;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;

import java.util.List;

@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {
	private final AuditDatabase auditDatabase;

	@Override
	public void save(String username, UserAction userAction) {
		auditDatabase.saveUserAction(username, userAction);
	}

	@Override
	public List<UserAction> findUserActionByUsername(String username) {
		return auditDatabase.getUserActions(username);
	}
}