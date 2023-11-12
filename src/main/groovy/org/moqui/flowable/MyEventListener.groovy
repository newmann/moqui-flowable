package org.moqui.flowable

import groovy.json.JsonGenerator
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType
import org.flowable.common.engine.api.delegate.event.FlowableEvent
import org.flowable.common.engine.api.delegate.event.FlowableEventListener
import org.flowable.engine.ProcessEngine
import org.flowable.engine.ProcessEngines
import org.flowable.engine.RepositoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.delegate.event.impl.FlowableActivityEventImpl
import org.flowable.engine.runtime.ActivityInstance
import org.slf4j.LoggerFactory

class MyEventListener implements FlowableEventListener{
    protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(MyEventListener.class)

    @Override
    void onEvent(FlowableEvent event) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine()
        RuntimeService runtimeService = processEngine.getRuntimeService()
        logger.info("收到 Event -> type:${event.type}, " + event.toString())
        if(event.getType() == FlowableEngineEventType.ACTIVITY_STARTED){
            FlowableActivityEventImpl activityEvent = (FlowableActivityEventImpl) event
            if (activityEvent.activityType.equals("sendEventServiceTask")){
                ActivityInstance activityInstance = runtimeService.createActivityInstanceQuery().activityId(activityEvent.activityId).singleResult()
                logger.info("Current Activity -> activityId: ${activityEvent.activityId}, activityName: ${activityEvent.activityName}, activityType: ${activityEvent.activityType}, behaviorClass: ${activityEvent.behaviorClass} ")
                logger.info("Current Activity -> executionId: ${activityInstance.executionId}")

                Map instanceVariable = runtimeService.getVariables(activityInstance.executionId)
                String jsonVariables = ''
                if(instanceVariable){
                    jsonVariables =  new JsonGenerator.Options().disableUnicodeEscaping().build().toJson(instanceVariable)
                }
                logger.info("Current Process Variables -> ${jsonVariables}")
                jsonVariables = ''
                Map localVariable = runtimeService.getVariablesLocal(activityInstance.executionId)
                if(localVariable){
                    jsonVariables =new JsonGenerator.Options().disableUnicodeEscaping().build().toJson(localVariable)
                }
                logger.info("Current Execution local Variables -> ${jsonVariables}")
            }

        }

    }

    @Override
    boolean isFailOnException() {
        return false
    }

    @Override
    boolean isFireOnTransactionLifecycleEvent() {
        return false
    }

    @Override
    String getOnTransaction() {
        return null
    }
}
