package com.playssafy.playssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PlayssafyApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlayssafyApplication.class, args);
	}
}
