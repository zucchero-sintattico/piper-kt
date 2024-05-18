package piperkt.services.servers.application.servers

import base.UnitTest
import org.mockito.kotlin.mock
import piperkt.events.ServerEventPublisher
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerId
import piperkt.services.servers.domain.factory.ServerFactory

open class BasicServerServiceTest : UnitTest() {
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
