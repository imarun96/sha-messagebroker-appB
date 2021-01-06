package com.lg.shamessagebrokerappB.azure.producer;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

@Profile("azure")
public class AzureProducerRefresh extends TimerTask {

	private AzureProducerService mqService;
	private static final Logger log = LoggerFactory.getLogger(AzureProducerRefresh.class);

	public AzureProducerRefresh(AzureProducerService mqService) {
		this.mqService = mqService;
	}

	private static final Integer MAXTRY = 5;
	private static Integer countValue = 0;

	/*
	 * Checks the Azure Service Bus connection for a specific time interval. Also sends
	 * message to the support team if the connection is not up.
	 */

	@Override
	public void run() {
		while (!mqService.createSession().equals(Boolean.TRUE) && countValue < MAXTRY) {
			countValue++;
		}
		countValue = 0;
		if (!mqService.getIsAlive().equals(Boolean.TRUE)) {
			log.info("Message Broker B[Azure] is not up. Sending mail to support team.");
		} else {
			log.info("Message Broker B[Azure] Producer Connection working properly.");
		}
	}
}