/*
 * This software is in the public domain under CC0 1.0 Universal plus a 
 * Grant of Patent License.
 * 
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 * 
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package org.moqui.flowable

import groovy.transform.CompileStatic
import org.flowable.app.api.AppManagementService
import org.flowable.app.api.AppRepositoryService
import org.flowable.app.engine.AppEngineConfiguration
import org.flowable.app.engine.AppEngines
import org.flowable.common.engine.impl.AbstractEngineConfiguration
import org.flowable.engine.DynamicBpmnService
import org.flowable.engine.HistoryService
import org.flowable.engine.ManagementService
import org.flowable.engine.ProcessEngine
import org.flowable.engine.ProcessEngineConfiguration
import org.flowable.engine.ProcessEngines
import org.flowable.engine.ProcessMigrationService
import org.flowable.engine.RepositoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration
import org.flowable.eventregistry.api.ChannelDefinition
import org.flowable.eventregistry.api.EventDeployment
import org.flowable.eventregistry.api.EventManagementService
import org.flowable.eventregistry.api.EventRegistry
import org.flowable.eventregistry.api.EventRepositoryService
import org.flowable.eventregistry.api.model.InboundChannelModelBuilder
import org.flowable.eventregistry.impl.EventRegistryEngine
import org.flowable.eventregistry.impl.EventRegistryEngineConfiguration
import org.flowable.eventregistry.impl.EventRegistryEngines
import org.flowable.app.engine.AppEngine
import org.flowable.eventregistry.impl.configurator.EventRegistryEngineConfigurator
import org.flowable.eventregistry.model.ChannelModel
import org.flowable.eventregistry.spring.rabbit.RabbitChannelDefinitionProcessor
import org.moqui.context.ExecutionContext
import org.moqui.context.ExecutionContextFactory
import org.moqui.context.ToolFactory
import org.moqui.impl.service.rabbitmq.RabbitmqToolFactory
import org.moqui.resource.ResourceReference
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext

@CompileStatic
class FlowableToolFactory implements ToolFactory<FlowableToolFactory> {
    protected final static Logger logger = LoggerFactory.getLogger(FlowableToolFactory.class)
    final static String TOOL_NAME = "FLOWABLE"

    protected ExecutionContextFactory ecf = null

    ProcessEngine processEngine = null
    RuntimeService runtimeService = null
    RepositoryService repositoryService = null
    TaskService taskService =null
    HistoryService historyService = null
    ManagementService managementService = null
    DynamicBpmnService dynamicBpmnService = null
    ProcessMigrationService processMigrationService = null

//    EventRegistryEngineConfiguration eventRegistryEngineConfiguration = null
    EventRegistryEngine eventRegistryEngine = null
    EventRepositoryService eventRepositoryService = null
    EventRegistry eventRegistry = null
    EventManagementService eventManagementService = null

    AppEngine appEngine = null
    AppRepositoryService appRepositoryService=null
    AppManagementService appManagementService = null


    ConfigurableApplicationContext flowableContext = null

    RabbitChannelDefinitionProcessor rabbitChannelDefinitionProcessor = null

    /** Default empty constructor */
    FlowableToolFactory() { }

    @Override
    String getName() { return TOOL_NAME }
    @Override
    void init(ExecutionContextFactory ecf) {
        this.ecf = ecf

        flowableContext = SpringApplication.run(InitFlowableEngine.class)
//        String[] beans = flowableContext.getBeanDefinitionNames()
//        logger.info("List all flowable context beans")
//        for(String bean in beans){
//            logger.info(bean)
//        }
//        logger.info("---------------------------------")
        logger.info("Get Process Engine ... ")
//       processEngine
//        runtimeServiceBean
//        repositoryServiceBean
//        taskServiceBean
//        historyServiceBean
//        managementServiceBean
//        dynamicBpmnServiceBean
//        processInstanceMigrationService

//        identityServiceBean
//        idmEngine
//        idmManagementService
//        idmIdentityService

        processEngine = (ProcessEngine) flowableContext.getBean("processEngine")
        runtimeService = (RuntimeService) flowableContext.getBean("runtimeServiceBean")
        repositoryService = (RepositoryService) flowableContext.getBean("repositoryServiceBean")
        taskService = (TaskService) flowableContext.getBean("taskServiceBean")
        historyService = (HistoryService) flowableContext.getBean("historyServiceBean")
        managementService = (ManagementService) flowableContext.getBean("managementServiceBean")
        dynamicBpmnService = (DynamicBpmnService) flowableContext.getBean("dynamicBpmnServiceBean")
        processMigrationService = (ProcessMigrationService) flowableContext.getBean("processInstanceMigrationService")

        logger.info("Get App Engine and relate services ... ")
//        flowableAppEngine
//        appRepositoryServiceBean
//        appManagementServiceBean
        appEngine = (AppEngine) flowableContext.getBean("flowableAppEngine")
        appRepositoryService = (AppRepositoryService) flowableContext.getBean("appRepositoryServiceBean")
        appManagementService = (AppManagementService) flowableContext.getBean("appManagementServiceBean")


        logger.info("Get Event Registry Engine ... ")
//        eventRegistryEngine
//        eventRepositoryService
//        eventManagementService
//        eventRegistry

        eventRegistryEngine = (EventRegistryEngine) flowableContext.getBean("eventRegistryEngine")
        eventRepositoryService = (EventRepositoryService) flowableContext.getBean("eventRepositoryService")
        eventManagementService = (EventManagementService) flowableContext.getBean("eventManagementService")
        eventRegistry = (EventRegistry) flowableContext.getBean("eventRegistry")

//        cmmnEngine
//        cmmnRuntimeService
//        dynamicCmmnService
//        cmmnTaskService
//        cmmnManagementService
//        cmmnRepositoryService
//        cmmnHistoryService
//        cmmnMigrationService

//        contentEngine
//        contentService
//        contentManagementService

//        dmnEngine
//        dmnManagementService
//        dmnRepositoryService
//        dmnRuleService
//        dmnHistoryService


    }
    @Override
    void preFacadeInit(ExecutionContextFactory ecf) {
//        logger.info("Initialize Flowable ProcessEngines ... ")

//        ProcessEngines.init()

//        EventRegistryEngines.init()
//        AppEngines.init()
    }

    @Override
    FlowableToolFactory getInstance(Object... parameters) {
        if (processEngine == null) throw new IllegalStateException("FlowableToolFactory not initialized")
        return this
    }

    @Override
    void destroy() {
//        // stop Flowable Engine to prevent more calls coming in
        try {
            logger.info("Stopping Flowable Engine...")
            flowableContext.close()
//            ProcessEngines.destroy()
//            EventRegistryEngines.destroy()
//            AppEngines.destroy()
//            logger.info("Flowable Engine stopped")
        } catch (Throwable t) { logger.error("Error in Flowable Engine stop phase", t) }
    }

//    protected RabbitChannelDefinitionProcessor createRabbitChannelDefinitionProcessor(){
//        RabbitmqToolFactory rabbitmqToolFactory = (RabbitmqToolFactory) ecf.getTool(RabbitmqToolFactory.TOOL_NAME, RabbitmqToolFactory.class)
//
//        RabbitTemplate rabbitTemplate = (RabbitTemplate) rabbitmqToolFactory.getBean("flowableAmqpTemplate")
//
//        RabbitChannelDefinitionProcessor definitionProcessor = new RabbitChannelDefinitionProcessor(eventRegistryEngineConfiguration.getObjectMapper())
//        RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry = new RabbitListenerEndpointRegistry()
//        definitionProcessor.setEndpointRegistry(rabbitListenerEndpointRegistry)
//        definitionProcessor.setRabbitOperations((RabbitOperations)rabbitTemplate)
//
//        return definitionProcessor
//    }
//    protected registerRabbitChannel(){
//        EventRepositoryService eventRepositoryService = eventRegistryEngine.getEventRepositoryService()
//        logger.info("eventRepositoryService: " + eventRepositoryService.toString())
//
//        List<ChannelDefinition> channels = eventRepositoryService.createChannelDefinitionQuery()
//                .implementation("rabbit")
//                .latestVersion()
//                .list()
//
//        if(channels){
//            for(channel in channels){
//                ChannelModel channelModel = eventRepositoryService.getChannelModelByKey(channel.key)
//                rabbitChannelDefinitionProcessor.registerChannelModel(channelModel,channel.tenantId,eventRegistryEngine.getEventRegistry(),eventRepositoryService,false)
//            }
//        }
//    }

    ExecutionContextFactory getEcf() { return ecf }
    ProcessEngine getProcessEngine(){return processEngine}
    RuntimeService getRuntimeService(){return runtimeService}
    RepositoryService getRepositoryService(){return repositoryService}
    TaskService getTaskService(){return taskService}
    HistoryService getHistoryService(){return historyService}
    ManagementService getManagementService(){return managementService}
    DynamicBpmnService getDynamicBpmnService(){return dynamicBpmnService}
    ProcessMigrationService getProcessMigrationService(){return processMigrationService}


    EventRegistryEngine getEventRegistryEngine(){return eventRegistryEngine}
    EventRepositoryService getEventRepositoryService(){return eventRepositoryService}
    EventRegistry getEventRegistry(){return eventRegistry}
    EventManagementService getEventManagementService(){return eventManagementService}



    AppEngine getAppEngine(){return appEngine}
    AppRepositoryService getAppRepositoryService(){return appRepositoryService}
    AppManagementService getAppManagementService(){return appManagementService}


}
