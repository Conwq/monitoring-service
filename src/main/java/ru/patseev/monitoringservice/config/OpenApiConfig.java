package ru.patseev.monitoringservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for OpenAPI setup.
 */
@Configuration
@ComponentScan(basePackages = {"org.springdoc"})
@Import({SpringDocConfiguration.class,
		SpringDocWebMvcConfiguration.class,
		org.springdoc.webmvc.ui.SwaggerConfig.class,
		SwaggerUiConfigProperties.class,
		SwaggerUiOAuthProperties.class,
		JacksonAutoConfiguration.class})
public class OpenApiConfig implements WebMvcConfigurer {

	/**
	 * Bean definition for OpenAPI configuration.
	 *
	 * @return An instance of OpenAPI.
	 */
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI();
	}
}
