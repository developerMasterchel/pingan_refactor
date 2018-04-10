package com.paic.arch.brokerblah.factory;

import static com.paic.arch.jmsbroker.SocketFinder.findNextAvailablePortBetween;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import com.paic.arch.brokerblah.broker.MyAbstrateBroker;
import com.paic.arch.brokerblah.broker.JmsBroker;
import com.paic.arch.brokerblah.broker.OtherBroker;
import com.paic.arch.brokerblah.constants.BrokerConstants;

/** 只负责创建
 * @author chen
 *
 */
public class MyBrokerFactory {
	
	private static final Logger LOG = getLogger(MyBrokerFactory.class);
	
	/** 默认创建activemq
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker createARunningEmbeddedBrokerOnAvailablePort() throws Exception {
		
        return createARunningEmbeddedBrokerAt(BrokerConstants.DEFAULT_BROKER_URL_PREFIX + findNextAvailablePortBetween(41616, 50000), BrokerConstants.TYPE_JMS_ACTIVEMQ);
    }
	
	/** 创建 type 代表的 代理 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker createARunningEmbeddedBrokerOnAvailablePort(String type) throws Exception {
		
        return createARunningEmbeddedBrokerAt(BrokerConstants.DEFAULT_BROKER_URL_PREFIX + findNextAvailablePortBetween(41616, 50000), type);
    }

	private static MyAbstrateBroker createARunningEmbeddedBrokerAt(String aBrokerUrl, String type) throws Exception {
		LOG.debug("Creating a new broker at {}", aBrokerUrl);
		MyAbstrateBroker broker = null;
		if (!StringUtils.isEmpty(type) && type.startsWith(BrokerConstants.TYPE_JMS)) {
			broker = new JmsBroker();
		}
		else {
			broker = new OtherBroker();
		}
		broker.setBrokerUrl(aBrokerUrl);
        broker.create(type);
        broker.start();
        LOG.debug("Created a new broker at {}", aBrokerUrl);
        return broker;
	}
	
}
