package com.paic.arch.brokerblah.utils;

import com.paic.arch.brokerblah.broker.JmsBroker;
import com.paic.arch.brokerblah.broker.MyAbstrateBroker;
import com.paic.arch.brokerblah.constants.BrokerConstants;
import com.paic.arch.brokerblah.factory.MyBrokerFactory;

/** 代理 向外 提供 入口的工具
 * @author chen
 *
 */
public class BrokerUtils {

	/** 创建jms 的 ACTIVEMQ的代理
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker createARunningEmbeddedBrokerOnAvailablePort() throws Exception {
		return MyBrokerFactory.createARunningEmbeddedBrokerOnAvailablePort(BrokerConstants.TYPE_JMS_ACTIVEMQ);
	}
	
	/** 绑定url到 ActiveMq
	 * @param aBrokerUrl
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker bindToActiveMqBrokerAt(String aBrokerUrl) throws Exception {
        MyAbstrateBroker broker = new JmsBroker();
        broker.setBrokerUrl(aBrokerUrl);
        return broker;
    }
	
	/** 绑定url到 IbmMq
	 * @param aBrokerUrl
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker bindToIbmMqBrokerAt(String aBrokerUrl) throws Exception {
        MyAbstrateBroker broker = new JmsBroker();
        broker.setBrokerUrl(aBrokerUrl);
        return broker;
    }
	
	/** 绑定url到 TibcoMq
	 * @param aBrokerUrl
	 * @return
	 * @throws Exception
	 */
	public static MyAbstrateBroker bindToTibcoMqBrokerAt(String aBrokerUrl) throws Exception {
        MyAbstrateBroker broker = new JmsBroker();
        broker.setBrokerUrl(aBrokerUrl);
        return broker;
    }
}
