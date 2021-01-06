package com.lg.shamessagebrokerappB.azure.producer;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;

@Profile("azure")
public class AzureProducerSession<S extends javax.jms.Session> {

	protected S session = null;
	private Boolean isAlive = Boolean.TRUE;
	private static final Logger log = LoggerFactory.getLogger(AzureProducerSession.class);
	private Connection connection;
	/*
	 * Creates a session in ActiveMQ.
	 * 
	 * @return boolean The connection status of ActiveMQ
	 */

	@SuppressWarnings("unchecked")
	public Boolean connect(String brokerURL) {
		try {
			log.info("Initializing Message Broker B[Azure] Producer Connection.");
			log.info("Creating Message Broker B[Azure] Producer ConnectionFactory.");
			ConnectionStringBuilder csb = new ConnectionStringBuilder(brokerURL);
			Hashtable<String, String> env = new Hashtable<>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
			env.put("connectionfactory.ServiceBusConnectionFactory",
					"amqps://" + csb.getEndpoint().getHost() + "?amqp.idleTimeout=120000&amqp.traceFrames=true");
			log.info("Creating Message Broker B[Azure] Producer Context.");
			Context context = new InitialContext(env);
			log.info("Creating Message Broker B[Azure] Producer ConnectionFactory.");
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ServiceBusConnectionFactory");
			log.info("Creating Message Broker B[Azure] Producer Connection.");
			connection = connectionFactory.createConnection();
			log.info("Creating Message Broker B[Azure] Producer Session.");
			session = (S) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			isAlive = Boolean.TRUE;
		} catch (JMSException | NamingException e) {
			log.error("Problem in creating connection with Message Broker B[Azure] Producer {}", e.getMessage());
			isAlive = Boolean.FALSE;
			return false;
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				log.error("Problem in closing the Connection. {}", e.getMessage());
			}
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