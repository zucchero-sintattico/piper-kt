package piperkt.services.servers.infrastructure.persistence.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.common.id.ChannelId
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.ChannelType
import piperkt.services.servers.domain.factory.ChannelFactory

@MicronautTest
class ServerRepositoryImplTest(private val serverRepository: ServerRepository) :
    StringSpec({
        "should create a new server" {
            serverRepository.save("serverName", "serverDescription", "owner").let {
                it.id.value shouldNotBe ""
                it.name shouldBe "serverName"
                it.description shouldBe "serverDescription"
                it.owner shouldBe "owner"
                it.users shouldBe listOf("owner")
                it.channels shouldBe emptyList()
            }
        }

        "should delete a server" {
            serverRepository.save("serverName", "serverDescription", "owner").also {
                serverRepository.deleteById(it.id)
            }
            serverRepository.findByMember("owner").size shouldBe 0
        }

        "should update a server" {
            val serverUpdated =
                serverRepository.save("serverName", "serverDescription", "owner").let {
                    serverCreated ->
                    serverCreated.updateName("newServerName")
                    serverCreated.updateDescription("newServerDescription")
                    serverRepository.update(serverCreated)
                }
            serverUpdated.let {
                it.id.value shouldBe it.id.value
                it.name shouldBe "newServerName"
                it.description shouldBe "newServerDescription"
                it.owner shouldBe "owner"
                it.users shouldBe listOf("owner")
                it.channels shouldBe emptyList()
            }
        }

        "should find servers from a member" {
            serverRepository.save("serverName", "serverDescription", "owner")
            serverRepository.save("serverName", "serverDescription", "member")
            serverRepository.findByMember("member").size shouldBe 1
        }

        "should create a channel" {
            val server = serverRepository.save("serverName", "serverDescription", "owner")
            val channel =
                ChannelFactory.createFromType(
                    server.channels.size.toString(),
                    "channelName",
                    "channelDescription",
                    "TEXT"
                )
            server.addChannel(channel)
            serverRepository.update(server).let {
                it.channels.size shouldBe 1
                it.channels[0].channelId shouldBe ChannelId("0")
                it.channels[0].name shouldBe "channelName"
                it.channels[0].description shouldBe "channelDescription"
                it.channels[0].type shouldBe ChannelType.TEXT
            }
        }

        "should delete a channel" {
            val server = serverRepository.save("serverName", "serverDescription", "owner")
            val channel =
                ChannelFactory.createFromType(
                    server.channels.size.toString(),
                    "channelName",
                    "channelDescription",
                    "TEXT"
                )
            server.addChannel(channel)
            server.removeChannel(channel)
            serverRepository.update(server).channels.size shouldBe 0
        }

        "should update a channel" {
            val server = serverRepository.save("serverName", "serverDescription", "owner")
            val channel =
                ChannelFactory.createFromType(
                    server.channels.size.toString(),
                    "channelName",
                    "channelDescription",
                    "TEXT"
                )
            server.addChannel(channel)
            channel.name = "newChannelName"
            channel.description = "newChannelDescription"
            serverRepository.update(server).let {
                it.channels.size shouldBe 1
                it.channels[0].channelId shouldBe ChannelId("0")
                it.channels[0].name shouldBe "newChannelName"
                it.channels[0].description shouldBe "newChannelDescription"
                it.channels[0].type shouldBe ChannelType.TEXT
            }
        }
    })
