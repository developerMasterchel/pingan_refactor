package com.paic.arch.brokerblah.broker;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.broker.BrokerService;

public abstract class MyAbstrateBroker {

	private BrokerService brokerService;
	
	private String brokerUrl;
	
	private String inputMessage;
	
	/** 创建
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public abstract MyAbstrateBroker create(String type) throws Exception;
	
	/** 消息
	 * @param message
	 * @return
	 */
	public abstract MyAbstrateBroker sendMessage(String message);
	
	/** 接受消息
	 * @param queueName
	 * @return
	 */
	public abstract String waitForAMessageOn(String queueName);
	
	/** 接受消息
	 * @param queueName
	 * @return
	 */
	public abstract String waitForAMessageOn(String queueName, final int aTimeout);
	
	/** 发送消息到
	 * @param queueName
	 * @return
	 */
	public abstract MyAbstrateBroker to(String queueName);
	
    public abstract long getEnqueuedMessageCountAt(String aDestinationName) throws Exception;

    public abstract boolean isEmptyQueueAt(String aDestinationName) throws Exception;
    
    //向下兼容 ------start-----
    public abstract MyAbstrateBroker sendATextMessageToDestinationAt(String aDestinationName, final String aMessageToSend);
    public abstract String retrieveASingleMessageFromTheDestination(String aDestinationName);
    public abstract String retrieveASingleMessageFromTheDestination(String aDestinationName, final int aTimeout);
    
    //向下兼容 -------end----
	
	public void start() throws Exception {
        brokerService.start();
    }
	
	public void stop() throws Exception {
        if (brokerService == null) {
            throw new IllegalStateException("Cannot stop the broker from this API: " +
                    "perhaps it was started independently from this utility");
        }
        brokerService.stop();
        brokerService.waitUntilStopped();
    }
	
	public MyAbstrateBroker andThen(){
		return this;
	}

	public BrokerService getBrokerService() {
		return brokerService;
	}

	public void setBrokerService(BrokerService brokerService) {
		this.brokerService = brokerService;
	}  
	
    public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getInputMessage() {
		return inputMessage;
	}

	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage;
	}


	interface Callback {
        String performFunction(Session aSession, Destination aDestination) throws JMSException;
    }
    
    public class NoMessageReceivedException extends RuntimeException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 4385730352351763838L;

		public NoMessageReceivedException(String reason) {
            super(reason);
        }
    }

}
