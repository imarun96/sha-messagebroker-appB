package com.lg.shamessagebrokerappB.sqs.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.lg.shamessagebrokerappB.common.dto.DHIS2ObjectDto;
import com.lg.shamessagebrokerappB.common.dto.OpenMRSObjectDto;

@Profile("sqs")
@Service
public interface SqsPublisherService {
    public String publish(OpenMRSObjectDto object);

    public String publish(DHIS2ObjectDto object);
}