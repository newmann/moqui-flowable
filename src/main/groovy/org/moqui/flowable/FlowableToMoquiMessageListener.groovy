package org.moqui.flowable

import groovy.json.JsonOutput
import org.moqui.impl.service.rabbitmq.RabbitmqToolFactory
import org.moqui.impl.service.rabbitmq.ToolMessageListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener

class FlowableToMoquiMessageListener extends ToolMessageListener implements MessageListener{
    //defined in rabbitmq.conf.xml
    final static String EXCHANGE = "flowable.moqui"
    final static String MOQUI_TO_FLOWABLE_ROUTE_KEY ="moqui.to.flowable"

    final static String AMQP_TEMPLATE="flowableAmpqTemplate"

    protected final static Logger logger = LoggerFactory.getLogger(FlowableToMoquiMessageListener.class)
    @Override
    void onMessage(Message message) {
//        logger.warn("ecf has binded:" + ecf.toString())
        logger.warn("Receive from flowable 信息体内容：${new String(message.body,'utf-8')}")

        HashMap<String,Object> runResult = new HashMap<>()
        runResult.put("businessKey", "Order:123456")
        runResult.put("serviceName", "JustforTest")
        runResult.put("eventKey","runMoquiServiceResultEvent")
        runResult.put("runResult",true)
        HashMap resultMsg = new HashMap()
        resultMsg.put("taskid", "123456")
        resultMsg.put("status", "Approved")
        runResult.put("jsonResultMsg",JsonOutput.toJson(resultMsg))
        try{
            AmqpTemplate amqpTemplate = (AmqpTemplate) ecf.getToolFactory(RabbitmqToolFactory.TOOL_NAME).getBean("flowableAmqpTemplate")
            amqpTemplate.convertAndSend(EXCHANGE,MOQUI_TO_FLOWABLE_ROUTE_KEY,runResult)
            logger.info("Send result message complete.")

        }catch (AmqpException e){
            ecf.getExecutionContext().getMessage().addError(e.message)
        }

    }


}
