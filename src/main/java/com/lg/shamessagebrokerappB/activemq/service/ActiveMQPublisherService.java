package com.lg.shamessagebrokerappB.activemq.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.lg.shamessagebrokerappB.common.dto.DHIS2ObjectDto;
import com.lg.shamessagebrokerappB.common.dto.OpenMRSObjectDto;

@Profile("activemq")
@Service
public interface ActiveMQPublisherService {
	public String publish(DHIS2ObjectDto object);

	public String publish(OpenMRSObjectDto object);
}