package com.lg.shamessagebrokerappB.activemq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lg.shamessagebrokerappB.ShaMessagebrokerAppBApplication;
import com.lg.shamessagebrokerappB.common.dto.DHIS2ObjectDto;
import com.lg.shamessagebrokerappB.common.dto.OpenMRSObjectDto;

@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@ActiveProfiles({ "activemq" })
@SpringBootTest(classes = ShaMessagebrokerAppBApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class ActiveMQControllerTest {

	@Autowired
	private ActiveMQController activeMQcontroller;

	@Test
	void saveActiveMQIntoDhis2() {
		DHIS2ObjectDto dhis2 = new DHIS2ObjectDto();
		dhis2.setBloodPressure("120/80");
		dhis2.setDataSet("clYapWbIvSb");
		dhis2.setHeight("175");
		dhis2.setOrgUnit("JWPKYub5hlq");
		dhis2.setPatientAddress("Uganda");
		dhis2.setPatientName("LG-USER");
		dhis2.setPeriod("202009");
		dhis2.setPulse("45");
		dhis2.setRespiratoryRate("23");
		dhis2.setTemperature("25");
		dhis2.setWeight("85");
		assertEquals("Message published to the queue.", activeMQcontroller.sendDHIS2MessageToQueue(dhis2));
	}

	@Test
	void saveActiveMQIntoOpenMRS() {
		OpenMRSObjectDto openMRS = new OpenMRSObjectDto();
		openMRS.setAddress1("Uganda");
		openMRS.setBirthdate("29-04-1996");
		openMRS.setCityVillage("Uganda");
		openMRS.setCountry("Uganda");
		openMRS.setFamilyName("Living-Goods");
		openMRS.setGender("M");
		openMRS.setGivenName("lg-sample-user");
		openMRS.setPostalCode("343434");
		assertEquals("Message published to the queue.", activeMQcontroller.sendOpenMRSMessageToQueue(openMRS));
	}
}