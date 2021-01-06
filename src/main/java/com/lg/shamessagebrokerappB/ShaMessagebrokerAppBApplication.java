package com.lg.shamessagebrokerappB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { ContextStackAutoConfiguration.class })
@EnableScheduling
@RefreshScope
@EnableJms
public class ShaMessagebrokerAppBApplication {

	private static final Logger log = LoggerFactory.getLogger(ShaMessagebrokerAppBApplication.class);

	public static void main(String[] args) {
		log.info("ShaMessagebrokerAppB service started.");
		SpringApplication.run(ShaMessagebrokerAppBApplication.class, args);
	}
}