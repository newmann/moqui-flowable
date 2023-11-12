package org.moqui.flowable

import org.flowable.engine.delegate.DelegateExecution
import org.slf4j.LoggerFactory

class Logger {
    protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class)

    void log(DelegateExecution execution, String message){{
        logger.info("LOG: Process {} activity {}: {}", execution.getProcessDefinitionId(), execution.getCurrentActivityId(), message)
        execution.getVariables().forEach( (name, value) -> logger.debug("LOG: var {} := {}", name, value))
    }}
}
