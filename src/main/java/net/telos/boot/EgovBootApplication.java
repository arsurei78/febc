package net.telos.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages="net.telos")
@EnableMongoRepositories(basePackages = "net.telos.web.repository.mongo")
@EnableScheduling
public class EgovBootApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(EgovBootApplication.class);
		springApplication.setLogStartupInfo(false);
		springApplication.run(args);
	}
}
