package ru.patseev.monitoringservice.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.patseev.monitoringservice.in.jwt.AuthInterceptor;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the monitoring application.
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.patseev.monitoringservice")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class MonitoringServiceConfig implements WebMvcConfigurer {

	/**
	 * URL for connecting to the database.
	 */
	@Value("${database.url}")
	public String url;

	/**
	 * Username for connecting to the database.
	 */
	@Value("${database.username}")
	public String username;

	/**
	 * Password for the database user.
	 */
	@Value("${database.password}")
	public String password;

	private final AuthInterceptor authInterceptor;

	@Autowired
	public MonitoringServiceConfig(AuthInterceptor authInterceptor) {
		this.authInterceptor = authInterceptor;
	}

	/**
	 * Creates and configures a data source (DataSource) for connecting to the database.
	 *
	 * @return DataSource for connecting to the database.
	 */
	@Bean
	public DataSource dataSource() {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();
		dataSource.setURL(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/meters/**", "/audits/**");
	}
}
