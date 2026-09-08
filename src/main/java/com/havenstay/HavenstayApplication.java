	package com.havenstay;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.scheduling.annotation.EnableAsync;

	@SpringBootApplication
	@EnableAsync
	public class HavenstayApplication{
		public static void main(String[] args){
			SpringApplication.run(HavenstayApplication.class, args);
		}

	}
