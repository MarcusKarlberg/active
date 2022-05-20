package se.mk.active;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Active API", version = "1.0",
		description = "API for municipalities and local governments to create venues and events"))
public class ActiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveApplication.class, args);
	}
}
