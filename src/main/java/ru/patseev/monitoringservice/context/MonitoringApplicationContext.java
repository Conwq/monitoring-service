package ru.patseev.monitoringservice.context;

import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.audit_service.db.AuditDatabase;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.repository.impl.AuditRepositoryImpl;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.audit_service.service.impl.AuditServiceImpl;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.db.DataMeterDatabase;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;
import ru.patseev.monitoringservice.data_meter_service.repository.impl.DataMeterRepositoryImpl;
import ru.patseev.monitoringservice.data_meter_service.service.DataMeterService;
import ru.patseev.monitoringservice.data_meter_service.service.impl.DataMeterServiceImpl;
import ru.patseev.monitoringservice.in.Application;
import ru.patseev.monitoringservice.in.OperationHandler;
import ru.patseev.monitoringservice.in.handler.RegistrationOperationHandler;
import ru.patseev.monitoringservice.in.handler.SignInOperationHandler;
import ru.patseev.monitoringservice.in.session.OperationManager;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.db.UserDatabase;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;
import ru.patseev.monitoringservice.user_service.repository.impl.UserRepositoryImpl;
import ru.patseev.monitoringservice.user_service.service.UserService;
import ru.patseev.monitoringservice.user_service.service.impl.UserServiceImpl;

import java.util.Scanner;

public class MonitoringApplicationContext {
	private static MonitoringApplicationContext context;
	private final Scanner scanner = new Scanner(System.in);
	private final UserDatabase userDatabase = new UserDatabase();
	private final DataMeterDatabase dataMeterDatabase = new DataMeterDatabase();
	private final AuditDatabase auditDatabase = new AuditDatabase();
	private final AuditRepository auditRepository = new AuditRepositoryImpl(auditDatabase);
	private final AuditService auditService = new AuditServiceImpl(auditRepository);
	private final AuditController auditController = new AuditController(auditService);
	private final DataMeterRepository dataMeterRepository = new DataMeterRepositoryImpl(dataMeterDatabase);
	private final DataMeterService dataMeterService = new DataMeterServiceImpl(dataMeterRepository);
	private final DataMeterController dataMeterController = new DataMeterController(dataMeterService, auditService);
	private final OperationManager operationManager = new OperationManager(scanner, dataMeterController, auditController);
	private final UserSessionManager clientSessionManager =
			new UserSessionManager(scanner, dataMeterController, operationManager);
	private final UserRepository userRepository = new UserRepositoryImpl(userDatabase);
	private final UserService userService = new UserServiceImpl(userRepository);
	private final UserController userController = new UserController(userService, auditService);
	private final OperationHandler registrationOperation = new RegistrationOperationHandler(scanner, userController);
	private final OperationHandler signInOperationHandler =
			new SignInOperationHandler(scanner, userController, clientSessionManager);
	private final Application application = new Application(scanner, registrationOperation, signInOperationHandler);

	private MonitoringApplicationContext() {
	}

	public static MonitoringApplicationContext getContext() {
		if (context == null) {
			context = new MonitoringApplicationContext();
		}
		return context;
	}

	public void runApplication() {
		application.renderInterface();
	}
}