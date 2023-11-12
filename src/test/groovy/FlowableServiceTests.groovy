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
import spock.lang.*

import org.flowable.engine.ProcessEngine
import org.flowable.engine.RepositoryService
import org.flowable.engine.repository.Deployment
import org.flowable.engine.repository.ProcessDefinition

import org.moqui.Moqui
import org.moqui.context.ExecutionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.moqui.flowable.FlowableToolFactory


class FlowableServiceTests extends Specification {
    protected final static Logger logger = LoggerFactory.getLogger(FlowableServiceTests.class)

    @Shared
    ExecutionContext ec

    def setupSpec() {
        // init the framework, get the ec
        ec = Moqui.getExecutionContext()
    }

    def cleanupSpec() {
        ec.destroy()
    }

    def setup() {
        ec.user.loginUser("john.doe", "moqui")
        // we still have to disableAuthz even though a user is logged in because this user does not have permission to
        //     call this service directly (normally is called through a screen with inherited permission)
        ec.artifactExecution.disableAuthz()
        ec.transaction.begin(null)
    }

    def cleanup() {
        ec.transaction.commit()
        ec.artifactExecution.enableAuthz()
        ec.user.logoutUser()
    }

    def "run holiday-request"() {
        when:
        InputStream bpmnFile = ec.resource.getLocationStream("component://moqui-flowable/bpmn/holiday-request.bpmn20.xml")

        RepositoryService repositoryService = ec.getTool(FLOWABLE, ProcessEngine.class).getRepositoryService()
        Deployment deployment = repositoryService.createDeployment()
            .category("beiyeiln")
            .name("HolidayRequest")
            .addInputStream("bpmnFile",bpmnFile)
            .key("HolidayRequestKey")
            .deploy()

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult()
        then:
        processDefinition.name == "HolidayRequest"

    }
}
