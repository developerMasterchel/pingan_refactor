package com.paic.arch.brokerblah.constants;

/** 常量 类
 * @author chen
 *
 */
public final class BrokerConstants {
	
	/**
	 * 1秒
	 */
	private static final int ONE_SECOND = 1000;
	
    /**
     * 默认值 10秒
     */
    public static final int DEFAULT_RECEIVE_TIMEOUT = 10 * ONE_SECOND;
    
    /**
     * 默认本地 tcp
     */
    public static final String DEFAULT_BROKER_URL_PREFIX = "tcp://localhost:";
    
    /**
     *  代理模式..
     */
    public static final String TYPE_JMS = "jms_";
    
    public static final String TYPE_OTHER = "other_";
    
    public static final String TYPE_ACTIVEMQ = "activemq";
    
    public static final String TYPE_IBMMQ = "ibmmq";
    
    public static final String TYPE_TIBCOMQ = "tibcomq";
    
    public static final String TYPE_JMS_ACTIVEMQ = TYPE_JMS + TYPE_ACTIVEMQ;
    
    public static final String TYPE_JMS_IBMMQ = TYPE_JMS + TYPE_IBMMQ;
    
    public static final String TYPE_JMS_TIBCOMQ = TYPE_JMS + TYPE_TIBCOMQ;
    
    public static final String TYPE_OTHER_ACTIVEMQ = TYPE_OTHER + TYPE_ACTIVEMQ;
    
    public static final String TYPE_OTHER_IBMMQ = TYPE_OTHER + TYPE_IBMMQ;
    
    public static final String TYPE_OTHER_TIBCOMQ = TYPE_OTHER + TYPE_TIBCOMQ;

}
