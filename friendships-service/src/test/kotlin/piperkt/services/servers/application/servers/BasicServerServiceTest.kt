package piperkt.services.servers.application.servers

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.common.events.ServerEventPublisher
import piperkt.common.id.ServerId
import piperkt.services.friendships.application.ServerRepository
import piperkt.services.friendships.application.ServerService
import piperkt.services.friendships.domain.Server
import piperkt.services.friendships.domain.factory.ServerFactory

open class BasicServerServiceTest : AnnotationSpec() {
    val serverRepository = mock<ServerRepository>()
    val eventPublisher = mock<ServerEventPublisher>()
    val serverService = ServerService(serverRepository, eventPublisher)

    lateinit var simpleServerId: ServerId
    lateinit var simpleServer: Server

    @BeforeEach
    fun setup() {
        simpleServerId = ServerId("0")
        simpleServer =
            ServerFactory.createServer(
                name = "serverName",
                description = "serverDescription",
                owner = "owner",
                id = simpleServerId.value
            )
    }
}
