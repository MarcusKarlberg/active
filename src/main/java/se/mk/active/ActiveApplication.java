package se.mk.active;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ActiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveApplication.class, args);
	}

}
