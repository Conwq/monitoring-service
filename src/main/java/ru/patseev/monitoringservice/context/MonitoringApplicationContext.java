package ru.patseev.monitoringservice.context;

import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.repository.AuditRepository;
import ru.patseev.monitoringservice.repository.impl.AuditRepositoryImpl;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.impl.AuditServiceImpl;
import ru.patseev.monitoringservice.in.AuthenticationManager;
import ru.patseev.monitoringservice.in.handler.AbstractOperationHandler;
import ru.patseev.monitoringservice.in.handler.operation.RegistrationOperationHandler;
import ru.patseev.monitoringservice.in.handler.operation.SignInOperationHandler;
import ru.patseev.monitoringservice.in.session.OperationManager;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.manager.ConnectionProvider;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.repository.DataMeterRepository;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.repository.impl.DataMeterRepositoryImpl;
import ru.patseev.monitoringservice.repository.impl.MeterTypeRepositoryImpl;
import ru.patseev.monitoringservice.service.MeterService;
import ru.patseev.monitoringservice.service.impl.MeterServiceImpl;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.repository.RoleRepository;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.repository.impl.RoleRepositoryImpl;
import ru.patseev.monitoringservice.repository.impl.UserRepositoryImpl;
import ru.patseev.monitoringservice.service.UserService;
import ru.patseev.monitoringservice.service.impl.UserServiceImpl;

import java.util.Scanner;

/**
 * The MonitoringApplicationContext class represents the application context for the monitoring service.
 * It provides the necessary instances for various components like controllers, repositories, and services.
 * This class follows the Singleton pattern to ensure a single instance throughout the application.
 */
public class MonitoringApplicationContext {
	private static MonitoringApplicationContext context;
	private final Scanner scanner = new Scanner(System.in);
	private final PrinterMeterData printerMeterData = new PrinterMeterData();
	private final ResourceManager resourceManager = new ResourceManager("application");
	private final ConnectionProvider connectionProvider = new ConnectionProvider(resourceManager);

	/*
	 * Repositories
	 */
	private final UserRepository userRepository = new UserRepositoryImpl(connectionProvider);
	private final RoleRepository roleRepository = new RoleRepositoryImpl(connectionProvider);
	private final AuditRepository auditRepository = new AuditRepositoryImpl(connectionProvider);
	private final DataMeterRepository dataMeterRepository = new DataMeterRepositoryImpl(connectionProvider);
	private final MeterTypeRepository meterTypeRepository = new MeterTypeRepositoryImpl(connectionProvider);

	/*
	 * Services
	 */
	private final UserService userService = new UserServiceImpl(userRepository, roleRepository);
	private final AuditService auditService = new AuditServiceImpl(auditRepository);
	private final MeterService meterService = new MeterServiceImpl(dataMeterRepository, meterTypeRepository);

	/*
	 * Controllers
	 */
	private final UserController userController = new UserController(userService, auditService);
	private final AuditController auditController = new AuditController(auditService, userController);
	private final MeterController meterController = new MeterController(meterService, auditService);

	/*
	 * Operations
	 */
	private final OperationManager operationManager =
			new OperationManager(scanner, meterController, auditController, printerMeterData);
	private final UserSessionManager clientSessionManager =
			new UserSessionManager(scanner, meterController, operationManager);
	private final AbstractOperationHandler registrationOperation = new RegistrationOperationHandler(scanner, userController);
	private final AbstractOperationHandler signInOperationHandler =
			new SignInOperationHandler(scanner, userController, clientSessionManager);

	/*
	 * Application
	 */
	private final AuthenticationManager authenticationManager =
			new AuthenticationManager(scanner, registrationOperation, signInOperationHandler);

	private MonitoringApplicationContext() {
	}

	/**
	 * Retrieves the singleton instance of the MonitoringApplicationContext.
	 *
	 * @return The singleton instance of the MonitoringApplicationContext.
	 */
	public static MonitoringApplicationContext getContext() {
		if (context == null) {
			context = new MonitoringApplicationContext();
		}
		return context;
	}

	/**
	 * Runs the monitoring service application, starting with rendering the user interface.
	 */
	public void runApplication() {
		authenticationManager.renderInterface();
	}
}