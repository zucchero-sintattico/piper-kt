package piperkt.services.servers.domain

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.ServerFactory

class ServerTest : AnnotationSpec() {

    @Test
    fun `should allow to change the name`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        server.updateName("new-server-name")
        server.name shouldBe "new-server-name"
    }

    @Test
    fun `should allow to change the description`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        server.updateDescription("new-server-description")
        server.description shouldBe "new-server-description"
    }

    @Test
    fun `should allow to add a user to the server`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        server.addUser("user-id")
        server.users.size shouldBe 2
    }

    @Test
    fun `should allow to remove a user from the server`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        server.removeUser("owner")
        server.users.size shouldBe 0
    }

    @Test
    fun `should allow to add a channel to the server`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        val channel = ChannelFactory.createFromType("channelId", "name", "description", "TEXT")
        server.addChannel(channel)
        server.channels.size shouldBe 1
    }

    @Test
    fun `should allow to remove a channel from the server`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        val channel = ChannelFactory.createFromType("channelId", "name", "description", "TEXT")
        server.addChannel(channel)
        server.removeChannel(channel)
        server.channels.size shouldBe 0
    }

    @Test
    fun `should allow to update channel name and description`() {
        val server =
            ServerFactory.createServer("fakeServerId", "server-name", "server-description", "owner")
        val channel = ChannelFactory.createFromType("channelId", "name", "description", "TEXT")
        server.addChannel(channel)
        channel.name = "new-name"
        channel.description = "new-description"
        server.channels[0].name shouldBe "new-name"
        server.channels[0].description shouldBe "new-description"
    }
}
