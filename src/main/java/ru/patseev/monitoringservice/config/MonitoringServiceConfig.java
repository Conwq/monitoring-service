package ru.patseev.monitoringservice.config;

import lombok.RequiredArgsConstructor;
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
@PropertySource(value = "classpath:application.yml", factory = YmlProperties.class)
@EnableAspectJAutoProxy
@RequiredArgsConstructor
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

	/**
	 * The AuthInterceptor instance for intercepting HTTP requests.
	 */
	private final AuthInterceptor authInterceptor;

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

	/**
	 * Adds the AuthInterceptor to the interceptor registry to intercept HTTP requests.
	 *
	 * @param registry The InterceptorRegistry instance for registering interceptors.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/meters/**", "/audits/**");
	}
}