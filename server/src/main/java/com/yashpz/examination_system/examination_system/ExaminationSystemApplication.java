package com.yashpz.examination_system.examination_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@EnableScheduling
public class ExaminationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExaminationSystemApplication.class, args);
	}

}