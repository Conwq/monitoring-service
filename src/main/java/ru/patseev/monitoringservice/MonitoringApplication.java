package ru.patseev.monitoringservice;

import ru.patseev.monitoringservice.context.MonitoringApplicationContext;

/**
 * The MonitoringApplication class contains the main method to run the monitoring application.
 */
public class MonitoringApplication {

	public static void main(String[] args) {
		MonitoringApplicationContext context = MonitoringApplicationContext.getContext();
		context.runApplication();
	}
}