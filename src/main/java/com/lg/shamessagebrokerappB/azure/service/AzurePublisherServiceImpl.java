package com.lg.shamessagebrokerappB.azure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.shamessagebrokerappB.azure.producer.AzureProducerService;
import com.lg.shamessagebrokerappB.common.dto.DHIS2ObjectDto;
import com.lg.shamessagebrokerappB.common.dto.OpenMRSObjectDto;
import com.lg.shamessagebrokerappB.common.encryption.EncryptThePayload;

@Profile("azure")
@Service
public class AzurePublisherServiceImpl implements AzurePublisherService {

	private static final Logger log = LoggerFactory.getLogger(AzurePublisherServiceImpl.class);

	private static final String DHIS2_INSTANCE = "DHIS2";
	private static final String OPENMRS_INSTANCE = "OPENMRS";

	private AzureProducerService service;

	@Autowired
	public AzurePublisherServiceImpl(AzureProducerService service) {
		this.service = service;
	}

	/*
	 * Publishes OpenMRS message to Azure Service Bus.
	 * 
	 * @param object The message from the request
	 * 
	 * @return The response as a String
	 */

	@Override
	public String publish(OpenMRSObjectDto object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = StringUtils.EMPTY;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Problem in Converting Object into JSON {}", e.getMessage());
		}
		String encryptedMessage = EncryptThePayload.encrypt(jsonString);
		if (Boolean.TRUE.equals(service.sendMessage(encryptedMessage, OPENMRS_INSTANCE))) {
			return "Message published to the queue.";
		} else {
			return "Some error has occured. Reported to support team. Try again later.";
		}
	}

	/*
	 * Publishes DHIS2 message to Azure Service Bus.
	 * 
	 * @param object The message from the request
	 * 
	 * @return The response as a String
	 */

	@Override
	public String publish(DHIS2ObjectDto object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = StringUtils.EMPTY;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Problem in Converting Object into JSON {}", e.getMessage());
		}
		String encryptedMessage = EncryptThePayload.encrypt(jsonString);
		if (Boolean.TRUE.equals(service.sendMessage(encryptedMessage, DHIS2_INSTANCE))) {
			return "Message published to the queue.";
		} else {
			return "Some error has occured. Reported to support team. Try again later.";
		}
	}
}