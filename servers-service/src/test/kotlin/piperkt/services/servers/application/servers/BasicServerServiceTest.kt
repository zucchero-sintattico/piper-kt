package piperkt.services.servers.application.servers

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.common.events.ServerEventPublisher
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.application.ServerService

open class BasicServerServiceTest : AnnotationSpec() {
    val serverRepository = mock<ServerRepository>()
    val eventPublisher = mock<ServerEventPublisher>()
    val serverService = ServerService(serverRepository, eventPublisher)
}
