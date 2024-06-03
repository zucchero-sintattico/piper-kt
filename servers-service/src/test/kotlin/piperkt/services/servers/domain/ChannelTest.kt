package piperkt.services.servers.domain

import base.UnitTest
import io.kotest.matchers.shouldBe
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory

class ChannelTest : UnitTest() {

    private val simpleChannel = ChannelFactory.createFromType("name", "description", "TEXT")

    @Test
    fun `should add message to channel`() {
        val message = MessageFactory.createMessage("content", "sender")
        simpleChannel.addMessage(message)
        simpleChannel.channelMessages.size shouldBe 1
    }
}
