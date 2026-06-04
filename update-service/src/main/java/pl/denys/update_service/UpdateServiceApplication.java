package pl.denys.update_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class UpdateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateServiceApplication.class, args);
	}

}
