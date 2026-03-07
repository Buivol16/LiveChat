package pl.denys.gateway_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.config.FunctionsEndpointAutoConfiguration;

@SpringBootApplication(exclude = {FunctionsEndpointAutoConfiguration.class})
@Slf4j
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
}
