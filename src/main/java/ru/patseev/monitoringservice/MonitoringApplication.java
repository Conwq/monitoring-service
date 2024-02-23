package ru.patseev.monitoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.patseev.auditstarter.annotation.EnableAudit;

/**
 * Main class for starting the Monitoring Application.
 * It also enables auditing functionality using the EnableAudit annotation.
 */
@SpringBootApplication
@EnableAudit
public class MonitoringApplication {

	/**
	 * Main method to start the Monitoring Application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
}