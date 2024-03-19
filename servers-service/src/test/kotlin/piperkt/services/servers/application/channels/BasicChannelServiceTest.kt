package piperkt.services.servers.application.channels

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.ServerRepository

open class BasicChannelServiceTest : AnnotationSpec() {

    protected val channelRepository = mock<ChannelRepository>()
    protected val serverRepository = mock<ServerRepository>()
    protected val eventPublisher = mock<DomainEventPublisher>()
    protected val channelService =
        ChannelService(channelRepository, serverRepository, eventPublisher)
}
