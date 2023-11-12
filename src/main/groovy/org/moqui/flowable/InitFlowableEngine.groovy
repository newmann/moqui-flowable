package org.moqui.flowable

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(proxyBeanMethods = false)
class InitFlowableEngine {

    @Bean
    ObjectMapper ObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper()
        return objectMapper
    }

}
