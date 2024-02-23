package ru.patseev.timecountringstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for starting the Time Counting Starter.
 * It also enables auditing functionality using the EnableAudit annotation.
 */
@SpringBootApplication
public class TimecountringstarterApplication {

	/**
	 * Main method to start the Time Counting Starter.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TimecountringstarterApplication.class, args);
	}
}
