package ru.patseev.monitoringservice.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializes the DispatcherServlet for the monitoring service application.
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Specifies the root configuration classes.
	 *
	 * @return An array of root configuration classes or null if no root configuration is provided.
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	/**
	 * Specifies the servlet configuration classes.
	 *
	 * @return An array of servlet configuration classes.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{MonitoringServiceConfig.class};
	}

	/**
	 * Specifies the servlet mappings.
	 *
	 * @return An array of servlet mappings.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}