/*
 * Application entry point containing main method 
 * 
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan
 */

package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiveLoggingWebApp {

	public static void main(String[] args) {
		SpringApplication.run(DiveLoggingWebApp.class, args);
	}
}
