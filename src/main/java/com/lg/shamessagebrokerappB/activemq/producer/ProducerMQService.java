package com.lg.shamessagebrokerappB.activemq.producer;

import java.util.Timer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("activemq")
@Component
public class ProducerMQService {

    @Value("${activeMQ.dhis2Queue}")
    String dhis2Queue;

    @Value("${activeMQ.openMRSQueue}")
    String openMRSQueue;

    @Value("${activeMQ.brokerURL}")
    String brokerURL;

    @Value("${activeMQ.uname}")
    String uname;

    @Value("${activeMQ.password}")
    String password;

    private ProducerMQSender mqSender = new ProducerMQSender();
    private ProducerMQRefresh mqRefresh = new ProducerMQRefresh(this);
    private Boolean isAlive = Boolean.FALSE;
    private Boolean initialFlag = Boolean.TRUE;
    private Timer timer = new Timer();
    private static final String DHIS2_INSTANCE = "DHIS2";
    private static final String OPENMRS_INSTANCE = "OPENMRS";

    /*
     * Creates a session in Active MQ.
     * 
     * @return boolean The connection status of Active MQ
     */

    public Boolean createSession() {
        Boolean returnValue = mqSender.connect(brokerURL, uname, password);
        isAlive = mqSender.getIsAlive();
        initiateMonitor();
        return returnValue;
    }

    @PostConstruct
    public void initiateMethod() {
        createSession();
    }

    public void initiateMonitor() {
        if (initialFlag.equals(Boolean.TRUE)) {
            timer.schedule(mqRefresh, 60000, 100000);
        }
        initialFlag = Boolean.FALSE;
    }

    public Boolean sendMessage(String object, String instance) {
        if (instance.equals(DHIS2_INSTANCE)) {
            return mqSender.sendMessage(object, dhis2Queue);
        } else if (instance.equals(OPENMRS_INSTANCE)) {
            return mqSender.sendMessage(object, openMRSQueue);
        }
        return Boolean.FALSE;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }
}