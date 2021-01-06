package com.lg.shamessagebrokerappB.azure.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.lg.shamessagebrokerappB.common.dto.DHIS2ObjectDto;
import com.lg.shamessagebrokerappB.common.dto.OpenMRSObjectDto;

@Profile("azure")
@Service
public interface AzurePublisherService {
	public String publish(DHIS2ObjectDto object);

	public String publish(OpenMRSObjectDto object);
}