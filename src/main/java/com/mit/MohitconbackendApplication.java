package com.mit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.mit"})
@EnableJpaAuditing
public class MohitconbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MohitconbackendApplication.class, args);
	}

}
