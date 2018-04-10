package com.paic.arch.brokerblah.utils;

import org.apache.activemq.broker.BrokerService;

import com.paic.arch.brokerblah.broker.MyAbstrateBroker;

/** 管理jms 代理的 一些公有。。。。业务逻辑 处理 放在该类
 * @author chen
 *
 */
public class JmsUtils {
	
	/** 嵌入 Activemq 
	 * @param broker
	 * @throws Exception
	 */
	public static void createEmbeddedBrokerActivemq(MyAbstrateBroker broker) throws Exception {
		//    Activemq   addConnector
        BrokerService brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.addConnector(broker.getBrokerUrl());
        broker.setBrokerService(brokerService);
    }
	 
	 /** 嵌入 Ibmmq 
	 * @param broker
	 * @throws Exception
	 */
	public static void createEmbeddedBrokerIbmmq(MyAbstrateBroker broker) throws Exception {
		// 先用  Activemq 的方法垫着 占着坑
        BrokerService brokerService = broker.getBrokerService();
        brokerService.setPersistent(false);
        brokerService.addConnector(broker.getBrokerUrl());
    }
	 
	 /** 嵌入 Tibcomq 
	 * @param broker
	 * @throws Exception
	 */
	public static void createEmbeddedBrokerTibcomq(MyAbstrateBroker broker) throws Exception {
		// 先用  Activemq 的方法 占着坑 
        BrokerService brokerService = broker.getBrokerService();
        brokerService.setPersistent(false);
        brokerService.addConnector(broker.getBrokerUrl());
    }
	//TODO 暂时 未 将业务逻辑处理抽取完。
	

}
