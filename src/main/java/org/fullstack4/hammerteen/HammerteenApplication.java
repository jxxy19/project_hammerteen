package org.fullstack4.hammerteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HammerteenApplication {

	public static void main(String[] args) {
		SpringApplication.run(HammerteenApplication.class, args);
	}

}
