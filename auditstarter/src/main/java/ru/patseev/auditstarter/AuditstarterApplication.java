package ru.patseev.auditstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for starting the Audit Starter.
 * It also enables auditing functionality using the EnableAudit annotation.
 */
@SpringBootApplication
public class AuditstarterApplication {

	/**
	 * Main method to start the Audit Starter.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuditstarterApplication.class, args);
	}
}
