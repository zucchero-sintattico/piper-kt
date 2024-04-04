package piperkt.services.servers.domain

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory

class ChannelTest : AnnotationSpec() {

    private val simpleChannel = ChannelFactory.createFromType("name", "description", "TEXT")

    @Test
    fun `should add message to channel`() {
        val message = MessageFactory.createMessage("content", "sender")
        simpleChannel.addMessage(message)
        simpleChannel.messages.size shouldBe 1
    }
}
