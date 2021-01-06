package com.lg.shamessagebrokerappB.activemq.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

@Profile("activemq")
public class ProducerMQSession<S extends javax.jms.Session> {

	protected S session = null;
	private Boolean isAlive = Boolean.TRUE;
	private static final Logger log = LoggerFactory.getLogger(ProducerMQSession.class);

	/*
	 * Creates a session in ActiveMQ.
	 * 
	 * @return boolean The connection status of ActiveMQ
	 */

	@SuppressWarnings("unchecked")
	public Boolean connect(String brokerURL, String uname, String password) {
		log.info("Initializing Message Broker B[ActiveMQ] Producer Connection.");
		log.info("Creating Message Broker B[ActiveMQ] Producer ConnectionFactory.");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		try {
			log.info("Creating Message Broker B[ActiveMQ] Producer Connection.");
			Connection conn = connectionFactory.createConnection(uname, password);
			log.info("Starting Message Broker B[ActiveMQ] Producer Connection.");
			conn.start();
			log.info("Started Message Broker B[ActiveMQ] Producer Connection.");
			log.info("Creating Message Broker B[ActiveMQ] Producer Session.");
			session = (S) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			isAlive = Boolean.TRUE;
		} catch (JMSException e) {
			log.error("Problem in creating connection with Message Broker B[ActiveMQ] MQ {}", e.getMessage());
			isAlive = Boolean.FALSE;
			return false;
		}
		return true;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}
}