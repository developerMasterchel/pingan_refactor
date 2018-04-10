## 欢迎参加平安普惠面试-重构
项目中有一个支持Active MQ代理发送和接收消息的实现类，为简化在自动系统级测试中消息的发送和接收的过程。其主要用途为：

  - 部署应用程序
  - 启动应用程序
  - 通过测试用例来确保消息的发收正常

在测试中的典型用法如下所示：

    @Test
    public void systemProcessesTradesAndCreatesAccountRequests(){
    
        String accountingMessage = bindToBrokerAtUrl(url)
            .andThen().sendATextMessageToDestinationAt(inputQueue, incomingTradeMessage)
            .andThen().retrieveASingleMessageFromTheDestination(accountingQueue);
        assertThat(accountingMessage).isEqualTo(expectedAccountingRequestMessage);
    }

这个API能实现最初的需求。我们希望这个API不仅仅适用于该测试，而是能在所有应用程序消息传递中使用。然而，当我们使用在别的用例或考虑SOLID原则时，会发现一些问题：

  - 如果想要使用不同的代理技术，会发现其耦合度很高（OCP）
  - 如果想让一个类只做一件事（SRP），会发现其中实现了很多其他功能。 

若想得到一个流式API，可参考如下结构：


    @Test
    public void blah() {
        bindToActiveMqBrokerAt(url)
            .and().sendTheMessage(inputMessage).to(inputQueue)
            .andThen().waitForAMessageOn(accountingQueue);
            
        bindToIbmMqBrokerAt(url)
            .and().sendTheMessage(inputMessage).to(inputQueue)
            .andThen().waitForAMessageOn(accountingQueue);
            
        bindToTibcoMqBrokerAt(url)
            .and().sendTheMessage(inputMessage).to(inputQueue)
            .andThen().waitForAMessageOn(accountingQueue);
    }

同时，我们需保证新API的向下兼容性。
 

## 挑战
理解该API并细想其在测试环境和应用程序代码的应用(如果需要，我们可以使用依赖注入)。请您重构此项目。另外，请您在代码上标注相关的注释，以便我们理解您的思路。

如果您完成了题目并认为您的答案可行，那么请把您的答案和相关意见或想法提交给我们。若您通过测试，我们将会邀请您与我们见面并一起探讨相关内容。



## 评分标准
  - 所有测试用例需无错执行。
  - 需正确理解题目要求。
  - 良好的编码习惯及面向对象的设计思想。
  - 请在截止日期前提交您的答案。



## 其他
请填写文件 [FEEDBACK.md](FEEDBACK.md)