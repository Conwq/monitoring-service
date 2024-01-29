package ru.patseev.monitoringservice.context;

import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.audit_service.db.AuditDatabase;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.repository.impl.AuditRepositoryImpl;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.audit_service.service.impl.AuditServiceImpl;
import ru.patseev.monitoringservice.data_meter_service.controller.MeterController;
import ru.patseev.monitoringservice.data_meter_service.db.DataMeterDatabase;
import ru.patseev.monitoringservice.data_meter_service.db.MeterTypeDatabase;
import ru.patseev.monitoringservice.data_meter_service.repository.DataMeterRepository;
import ru.patseev.monitoringservice.data_meter_service.repository.MeterTypeRepository;
import ru.patseev.monitoringservice.data_meter_service.repository.impl.DataMeterRepositoryImpl;
import ru.patseev.monitoringservice.data_meter_service.repository.impl.MeterTypeRepositoryImpl;
import ru.patseev.monitoringservice.data_meter_service.service.MeterService;
import ru.patseev.monitoringservice.data_meter_service.service.impl.MeterServiceImpl;
import ru.patseev.monitoringservice.in.handler.AbstractOperationHandler;
import ru.patseev.monitoringservice.in.Application;
import ru.patseev.monitoringservice.in.handler.operation.RegistrationOperationHandler;
import ru.patseev.monitoringservice.in.handler.operation.SignInOperationHandler;
import ru.patseev.monitoringservice.in.session.OperationManager;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.db.UserDatabase;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;
import ru.patseev.monitoringservice.user_service.repository.impl.UserRepositoryImpl;
import ru.patseev.monitoringservice.user_service.service.UserService;
import ru.patseev.monitoringservice.user_service.service.impl.UserServiceImpl;

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

	//Databases
	private final UserDatabase userDatabase = new UserDatabase();
	private final AuditDatabase auditDatabase = new AuditDatabase();
	private final DataMeterDatabase dataMeterDatabase = new DataMeterDatabase();
	private final MeterTypeDatabase meterTypeDatabase = new MeterTypeDatabase();

	//Repositories
	private final UserRepository userRepository = new UserRepositoryImpl(userDatabase);
	private final AuditRepository auditRepository = new AuditRepositoryImpl(auditDatabase);
	private final DataMeterRepository dataMeterRepository = new DataMeterRepositoryImpl(dataMeterDatabase);
	private final MeterTypeRepository meterTypeRepository = new MeterTypeRepositoryImpl(meterTypeDatabase);

	//Services
	private final UserService userService = new UserServiceImpl(userRepository);
	private final AuditService auditService = new AuditServiceImpl(auditRepository);
	private final MeterService meterService = new MeterServiceImpl(dataMeterRepository, meterTypeRepository);

	//Controllers
	private final UserController userController = new UserController(userService, auditService);
	private final AuditController auditController = new AuditController(auditService);
	private final MeterController meterController = new MeterController(meterService, auditService);

	//Operations
	private final OperationManager operationManager =
			new OperationManager(scanner, meterController, auditController, printerMeterData);
	private final UserSessionManager clientSessionManager =
			new UserSessionManager(scanner, meterController, operationManager);
	private final AbstractOperationHandler registrationOperation = new RegistrationOperationHandler(scanner, userController);
	private final AbstractOperationHandler signInOperationHandler =
			new SignInOperationHandler(scanner, userController, clientSessionManager);

	//Application
	private final Application application = new Application(scanner, registrationOperation, signInOperationHandler);

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
		application.renderInterface();
	}
}