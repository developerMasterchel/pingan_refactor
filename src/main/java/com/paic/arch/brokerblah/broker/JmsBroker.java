package com.paic.arch.brokerblah.broker;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import com.paic.arch.brokerblah.constants.BrokerConstants;
import com.paic.arch.brokerblah.utils.JmsUtils;


/** jms代理
 * @author chen
 *
 */
public class JmsBroker extends MyAbstrateBroker {
	
	private static final Logger LOG = getLogger(JmsBroker.class);
	
	/** 创建代理
	 */
	@Override
	public MyAbstrateBroker create(String type) throws Exception {
		if (!StringUtils.isEmpty(type) && type.endsWith(BrokerConstants.TYPE_ACTIVEMQ)) {
			JmsUtils.createEmbeddedBrokerActivemq(this);
			
		}else if (!StringUtils.isEmpty(type) && type.endsWith(BrokerConstants.TYPE_IBMMQ)) {
			JmsUtils.createEmbeddedBrokerIbmmq(this);
		}
		else {
			JmsUtils.createEmbeddedBrokerTibcomq(this);
		}
		return this;
	}

	/**
	 * 设置消息
	 */
	@Override
	public MyAbstrateBroker sendMessage(String message) {
		setInputMessage(message);
		return this;
	}
	
	/**
	 * 发送到 队列 
	 */
	@Override
	public MyAbstrateBroker to(String queueName) {
		executeCallbackAgainstRemoteBroker(this.getBrokerUrl(), queueName, (aSession, aDestination) -> {
            MessageProducer producer = aSession.createProducer(aDestination);
            producer.send(aSession.createTextMessage(getInputMessage()));
            producer.close();
            return "";
        });
        return this;
	}

	/**
	 * 等待 消息
	 */
	@Override
	public String waitForAMessageOn(String queueName) {
		return waitForAMessageOn(queueName, BrokerConstants.DEFAULT_RECEIVE_TIMEOUT);
	}
	
	@Override
    public String waitForAMessageOn(String aDestinationName, final int aTimeout) {
        return executeCallbackAgainstRemoteBroker(getBrokerUrl(), aDestinationName, (aSession, aDestination) -> {
            MessageConsumer consumer = aSession.createConsumer(aDestination);
            Message message = consumer.receive(aTimeout);
            if (message == null) {
                throw new NoMessageReceivedException(String.format("No messages received from the broker within the %d timeout", aTimeout));
            }
            consumer.close();
            return ((TextMessage) message).getText();
        });
    }

	@Override
	public long getEnqueuedMessageCountAt(String aDestinationName) throws Exception {
		return getDestinationStatisticsFor(aDestinationName).getMessages().getCount();
	}

	 private DestinationStatistics getDestinationStatisticsFor(String aDestinationName) throws Exception {
	        Broker regionBroker = getBrokerService().getRegionBroker();
	        for (org.apache.activemq.broker.region.Destination destination : regionBroker.getDestinationMap().values()) {
	            if (destination.getName().equals(aDestinationName)) {
	                return destination.getDestinationStatistics();
	            }
	        }
	        throw new IllegalStateException(String.format("Destination %s does not exist on broker at %s", aDestinationName, getBrokerUrl()));
	    }

	@Override
	public boolean isEmptyQueueAt(String aDestinationName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	private String executeCallbackAgainstConnection(Connection aConnection, String aDestinationName, Callback aCallback) {
	    Session session = null;
	    try {
	        session = aConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Queue queue = session.createQueue(aDestinationName);
	        return aCallback.performFunction(session, queue);
	    } catch (JMSException jmse) {
	        LOG.error("Failed to create session on connection {}", aConnection);
	        throw new IllegalStateException(jmse);
	    } finally {
	        if (session != null) {
	            try {
	                session.close();
	            } catch (JMSException jmse) {
	                LOG.warn("Failed to close session {}", session);
	                throw new IllegalStateException(jmse);
	            }
	        }
	    }
	}
	
	private String executeCallbackAgainstRemoteBroker(String aBrokerUrl, String aDestinationName, Callback aCallback) {
        Connection connection = null;
        String returnValue = "";
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(aBrokerUrl);
            connection = connectionFactory.createConnection();
            connection.start();
            returnValue = executeCallbackAgainstConnection(connection, aDestinationName, aCallback);
        } catch (JMSException jmse) {
            LOG.error("failed to create connection to {}", aBrokerUrl);
            throw new IllegalStateException(jmse);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException jmse) {
                    LOG.warn("Failed to close connection to broker at []", aBrokerUrl);
                    throw new IllegalStateException(jmse);
                }
            }
        }
        return returnValue;
    }

	@Override
	public MyAbstrateBroker sendATextMessageToDestinationAt(String aDestinationName, String aMessageToSend) {
		setInputMessage(aMessageToSend);
		to(aDestinationName);
		return this;
	}

	@Override
	public String retrieveASingleMessageFromTheDestination(String aDestinationName) {
		return waitForAMessageOn(aDestinationName);
	}

	@Override
	public String retrieveASingleMessageFromTheDestination(String aDestinationName, int aTimeout) {
		return waitForAMessageOn(aDestinationName,aTimeout);
	}
	
}
