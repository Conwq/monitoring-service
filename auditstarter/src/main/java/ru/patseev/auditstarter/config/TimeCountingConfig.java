package ru.patseev.auditstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.patseev.auditstarter.aspect.TimeCountingAspect;

/**
 * Configuration class responsible for configuring time counting aspect functionality in the application.
 * This class defines a bean for the TimeCountingAspect, which measures the execution time of methods.
 */
@Configuration
public class TimeCountingConfig {

	/**
	 * Defines a bean for TimeCountingAspect, responsible for measuring the execution time of methods.
	 *
	 * @return TimeCountingAspect instance
	 */
	@Bean
	public TimeCountingAspect timeCountingAspect() {
		return new TimeCountingAspect();
	}
}
