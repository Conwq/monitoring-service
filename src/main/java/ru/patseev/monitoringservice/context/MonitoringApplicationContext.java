package ru.patseev.monitoringservice.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;
import ru.patseev.monitoringservice.in.operation.manager.impl.AuditOperationManager;
import ru.patseev.monitoringservice.in.operation.manager.impl.MeterOperationManager;
import ru.patseev.monitoringservice.in.operation.manager.impl.UserOperationManager;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.Migration;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.*;
import ru.patseev.monitoringservice.repository.impl.*;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.MeterService;
import ru.patseev.monitoringservice.service.UserService;
import ru.patseev.monitoringservice.service.impl.AuditServiceImpl;
import ru.patseev.monitoringservice.service.impl.MeterServiceImpl;
import ru.patseev.monitoringservice.service.impl.UserServiceImpl;
import ru.patseev.monitoringservice.service.mapper.AuditMapper;
import ru.patseev.monitoringservice.service.mapper.MeterDataMapper;
import ru.patseev.monitoringservice.service.mapper.MeterTypeMapper;
import ru.patseev.monitoringservice.service.mapper.UserMapper;

/**
 * The MonitoringApplicationContext class represents the application context for the monitoring service.
 * It provides the necessary instances for various components like controllers, repositories, and services.
 * This class follows the Singleton pattern to ensure a single instance throughout the application.
 */
public class MonitoringApplicationContext {

	private static MonitoringApplicationContext context;
	private final UserMapper userMapper = UserMapper.instance;
	private final AuditMapper auditMapper = AuditMapper.instance;
	private final MeterTypeMapper meterTypeMapper = MeterTypeMapper.instance;
	private final MeterDataMapper meterDataMapper = MeterDataMapper.instance;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ObjectExtractor objectExtractor = new ObjectExtractor(objectMapper);
	private final ResponseGenerator responseGenerator = new ResponseGenerator(objectMapper);
	private final ResourceManager resourceManager = new ResourceManager("application");
	private final JwtService jwtService = new JwtService(resourceManager);
	private final ConnectionManager connectionManager = new ConnectionManager(resourceManager);
	private final Migration liquibaseMigration = new LiquibaseMigration(connectionManager, resourceManager);

	/*
	 * Repositories
	 */
	private final UserRepository userRepository = new UserRepositoryImpl(connectionManager);
	private final RoleRepository roleRepository = new RoleRepositoryImpl(connectionManager);
	private final AuditRepository auditRepository = new AuditRepositoryImpl(connectionManager);
	private final DataMeterRepository dataMeterRepository = new DataMeterRepositoryImpl(connectionManager);
	private final MeterTypeRepository meterTypeRepository = new MeterTypeRepositoryImpl(connectionManager);

	/*
	 * Services
	 */
	private final UserService userService = new UserServiceImpl(userRepository, roleRepository, userMapper);
	private final AuditService auditService = new AuditServiceImpl(auditRepository, auditMapper);
	private final MeterService meterService = new MeterServiceImpl(dataMeterRepository, meterTypeRepository, meterTypeMapper, meterDataMapper);

	/*
	 * Controllers
	 */
	private final UserController userController = new UserController(userService, auditService, jwtService);
	private final MeterController meterController = new MeterController(meterService, auditService, jwtService);
	private final AuditController auditController = new AuditController(auditService, userController, jwtService);

	/*
	 * Operation manager
	 */
	private final OperationManager userOperationManager
			= new UserOperationManager(userController, responseGenerator, objectExtractor);

	private final OperationManager meterOperationManager
			= new MeterOperationManager(meterController, responseGenerator, objectExtractor);

	private final OperationManager auditOperationManager
			= new AuditOperationManager(auditController, responseGenerator);

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
		liquibaseMigration.performMigration();
	}

	public MeterController getMeterController() {
		return meterController;
	}

	public JwtService getJwtService() {
		return jwtService;
	}

	public OperationManager getUserOperationManager() {
		return userOperationManager;
	}

	public OperationManager getMeterOperationManager() {
		return meterOperationManager;
	}

	public OperationManager getAuditOperationManager() {
		return auditOperationManager;
	}

	public ResponseGenerator getResponseGenerator() {
		return responseGenerator;
	}

	public AuditService getAuditService() {
		return auditService;
	}
}