package piperkt.services.servers.infrastructure.persistence.repository

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.ChannelType
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.ServerFactory

class ServerRepositoryImplTest(private val serverRepository: ServerRepository) : IntegrationTest() {

    @Test
    fun `should create a new server`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        serverRepository.save(server)
        val entity = serverRepository.findByMember("owner")
        entity.size shouldBe 1
        entity[0].let {
            it.id.value shouldBe it.id.value
            it.name shouldBe "serverName"
            it.description shouldBe "serverDescription"
            it.owner shouldBe "owner"
            it.users shouldBe listOf("owner")
            it.channels shouldBe emptyList()
        }
    }

    @Test
    fun `should delete a server`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        serverRepository.save(server)
        serverRepository.deleteById(server.id)
        serverRepository.findByMember("owner").size shouldBe 0
    }

    @Test
    fun `should update a server`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        serverRepository.save(server)
        server.name = "newServerName"
        server.description = "newServerDescription"
        serverRepository.update(server)
        serverRepository.findById(server.id)?.let {
            it.id.value shouldBe it.id.value
            it.name shouldBe "newServerName"
            it.description shouldBe "newServerDescription"
            it.owner shouldBe "owner"
            it.users shouldBe listOf("owner")
            it.channels shouldBe emptyList()
        }
    }

    @Test
    fun `should find servers from a member`() {
        val aliceServer = ServerFactory.createServer("serverName", "serverDescription", "alice")
        val bobServer = ServerFactory.createServer("serverName", "serverDescription", "bob")
        serverRepository.save(aliceServer)
        serverRepository.save(bobServer)
        serverRepository.findByMember("alice").size shouldBe 1
    }

    @Test
    fun `should create a channel`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        val channel = ChannelFactory.createFromType("channelName", "channelDescription", "TEXT")
        server.addChannel(channel)
        serverRepository.update(server)
        serverRepository.findById(server.id)?.let {
            it.channels.size shouldBe 1
            it.channels[0].id shouldBe channel.id
            it.channels[0].name shouldBe "channelName"
            it.channels[0].description shouldBe "channelDescription"
            it.channels[0].type shouldBe ChannelType.TEXT
        }
    }

    @Test
    fun `should delete a channel`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        val channel = ChannelFactory.createFromType("channelName", "channelDescription", "TEXT")
        server.addChannel(channel)
        serverRepository.update(server)
        serverRepository.findById(server.id)?.let {
            it.channels.size shouldBe 1
            it.channels[0].id shouldBe channel.id
        }
        server.removeChannel(channel)
        serverRepository.update(server)
        serverRepository.findById(server.id)?.let { it.channels.size shouldBe 0 }
    }

    @Test
    fun `should update a channel`() {
        val server = ServerFactory.createServer("serverName", "serverDescription", "owner")
        val channel = ChannelFactory.createFromType("channelName", "channelDescription", "TEXT")
        server.addChannel(channel)
        channel.name = "newChannelName"
        channel.description = "newChannelDescription"
        serverRepository.update(server)
        serverRepository.findById(server.id)?.let {
            it.channels.size shouldBe 1
            it.channels[0].name shouldBe "newChannelName"
            it.channels[0].description shouldBe "newChannelDescription"
        }
    }
}
