package com.micropos.posorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;


@SpringBootApplication(scanBasePackages = { "com.micropos.posorder.*" })
// @EnableDiscoveryClient
public class PosOrderApplication {

	public static final Logger log = LoggerFactory.getLogger(PosOrderApplication.class);

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "order");
		SpringApplication.run(PosOrderApplication.class, args);
	}

	@Bean
	public DirectChannel sendOrderChannel(){
		return new DirectChannel();
	}

}
