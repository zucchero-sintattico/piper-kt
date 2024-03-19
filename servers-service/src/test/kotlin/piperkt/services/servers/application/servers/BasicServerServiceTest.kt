package piperkt.services.servers.application.servers

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.application.ServerService

open class BasicServerServiceTest : AnnotationSpec() {
    val serverRepository = mock<ServerRepository>()
    val eventPublisher = mock<DomainEventPublisher>()
    val serverService = ServerService(serverRepository, eventPublisher)
}
