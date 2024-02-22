package ru.patseev.auditstarter.config;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.patseev.auditstarter.annotation.EnableAudit;
import ru.patseev.auditstarter.aspect.AuditAspect;
import ru.patseev.auditstarter.manager.ActionManager;
import ru.patseev.auditstarter.repository.AuditRepository;
import ru.patseev.auditstarter.repository.impl.AuditRepositoryImpl;
import ru.patseev.auditstarter.service.AuditService;
import ru.patseev.auditstarter.service.JwtAspectService;
import ru.patseev.auditstarter.service.impl.AuditServiceImpl;
import ru.patseev.auditstarter.service.mapper.AuditMapper;

import javax.sql.DataSource;

/**
 * Configuration class responsible for configuring auditing functionality in the application.
 * This class defines beans for various auditing components such as repositories, services, mappers, and aspects.
 */
@Configuration
public class AuditConfig {

	private final DataSource dataSource;

	/**
	 * Constructs a new instance of AuditConfig with the specified DataSource.
	 *
	 * @param dataSource the DataSource to be used for auditing
	 */
	@Autowired
	public AuditConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Defines a bean for JwtAspectService, responsible for managing JWT aspects.
	 *
	 * @return JwtAspectService instance
	 */
	@Bean
	public JwtAspectService jwtAspectService() {
		return new JwtAspectService();
	}

	/**
	 * Defines a bean for ActionManager, responsible for managing audit actions.
	 *
	 * @return ActionManager instance
	 */
	@Bean
	public ActionManager actionManager() {
		return new ActionManager();
	}

	/**
	 * Defines a bean for AuditRepository, responsible for data access related to audits.
	 *
	 * @return AuditRepository instance
	 */
	@Bean
	public AuditRepository auditRepository() {
		return new AuditRepositoryImpl(dataSource);
	}

	/**
	 * Defines a bean for AuditMapper, responsible for mapping audit entities.
	 *
	 * @return AuditMapper instance
	 */
	@Bean
	public AuditMapper auditMapper() {
		return Mappers.getMapper(AuditMapper.class);
	}

	/**
	 * Defines a bean for AuditService, responsible for audit-related business logic.
	 *
	 * @return AuditService instance
	 */
	@Bean
	public AuditService auditService() {
		return new AuditServiceImpl(auditRepository(), auditMapper());
	}

	/**
	 * Defines a bean for AuditAspect, responsible for applying auditing aspects.
	 *
	 * @return AuditAspect instance
	 */
	@Bean
	public AuditAspect auditAspect() {
		return new AuditAspect(jwtAspectService(), auditService(), actionManager());
	}
}
