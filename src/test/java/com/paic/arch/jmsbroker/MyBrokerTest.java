package com.paic.arch.jmsbroker;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paic.arch.brokerblah.broker.MyAbstrateBroker;
import com.paic.arch.brokerblah.utils.BrokerUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MyBrokerTest {

    public static final String TEST_QUEUE = "MY_TEST_QUEUE";
    public static final String MESSAGE_CONTENT = "Lorem blah blah";
    private static MyAbstrateBroker JMS_SUPPORT;
    private static String REMOTE_BROKER_URL;

    @BeforeClass
    public static void setup() throws Exception {
        JMS_SUPPORT = BrokerUtils.createARunningEmbeddedBrokerOnAvailablePort();
        REMOTE_BROKER_URL = JMS_SUPPORT.getBrokerUrl();
    }
    
    

    @AfterClass
    public static void teardown() throws Exception {
        JMS_SUPPORT.stop();
    }

    @Test
    public void sendsMessagesToTheRunningBroker() throws Exception {
    	BrokerUtils.bindToActiveMqBrokerAt(REMOTE_BROKER_URL)
                .andThen().sendMessage(MESSAGE_CONTENT).to(TEST_QUEUE);
        long messageCount = JMS_SUPPORT.getEnqueuedMessageCountAt(TEST_QUEUE);
        assertThat(messageCount).isEqualTo(1);
    }

    @Test
    public void readsMessagesPreviouslyWrittenToAQueue() throws Exception {
        String receivedMessage = BrokerUtils.bindToActiveMqBrokerAt(REMOTE_BROKER_URL)
                .sendMessage(MESSAGE_CONTENT).to(TEST_QUEUE)
                .andThen().waitForAMessageOn(TEST_QUEUE);
        assertThat(receivedMessage).isEqualTo(MESSAGE_CONTENT);
    }

    @Test(expected = MyAbstrateBroker.NoMessageReceivedException.class)
    public void throwsExceptionWhenNoMessagesReceivedInTimeout() throws Exception {
    	BrokerUtils.bindToActiveMqBrokerAt(REMOTE_BROKER_URL).waitForAMessageOn(TEST_QUEUE, 1);
    }


}