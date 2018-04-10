package com.paic.arch.brokerblah.broker;

/** 其他代理。。（假想有个其他的代理）
 * @author chen
 *
 */
public class OtherBroker extends MyAbstrateBroker {

	@Override
	public MyAbstrateBroker create(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyAbstrateBroker sendMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String waitForAMessageOn(String queueName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyAbstrateBroker to(String queueName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getEnqueuedMessageCountAt(String aDestinationName) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmptyQueueAt(String aDestinationName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String waitForAMessageOn(String queueName, int aTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyAbstrateBroker sendATextMessageToDestinationAt(String aDestinationName, String aMessageToSend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String retrieveASingleMessageFromTheDestination(String aDestinationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String retrieveASingleMessageFromTheDestination(String aDestinationName, int aTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
