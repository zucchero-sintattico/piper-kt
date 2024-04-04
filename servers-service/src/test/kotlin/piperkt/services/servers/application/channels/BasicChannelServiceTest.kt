package piperkt.services.servers.application.channels

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.common.events.ChannelEventPublisher
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.ServerRepository

open class BasicChannelServiceTest : AnnotationSpec() {

    protected val serverRepository = mock<ServerRepository>()
    protected val eventPublisher = mock<ChannelEventPublisher>()
    protected val channelService = ChannelService(serverRepository, eventPublisher)
}
