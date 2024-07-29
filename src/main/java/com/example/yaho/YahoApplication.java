package com.example.yaho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YahoApplication {
	//테스트중..

	public static void main(String[] args) {
		SpringApplication.run(YahoApplication.class, args);
	}

}
