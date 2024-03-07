package piperkt.services.servers.infrastructure

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.exception.ServerNotFoundException
import piperkt.services.servers.application.request.CreateServerRequest

@MicronautTest
class ServerServiceTest(private val serverService: ServerService) :
    StringSpec({
        "should create server" {
            val request = CreateServerRequest("name", "description", "owner")
            val server = serverService.createServer(request)
            server.name shouldBe "name"
            server.description shouldBe "description"
            server.owner shouldBe "owner"
        }

        "should get servers from user" {
            serverService.createServer(CreateServerRequest("name", "description", "owner"))
            serverService.getServersFromUser("owner").size shouldBe 1
        }

        "should delete server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.deleteServer(it.id.value)
            }
            serverService.getServersFromUser("owner").size shouldBe 0
        }

        "should let user join server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.addMemberToServer(it.id.value, "member")
            }
            serverService.getServersFromUser("member").size shouldBe 1
        }

        "should let user leave server" {
            serverService
                .createServer(CreateServerRequest("name", "description", "owner"))
                .also { serverService.addMemberToServer(it.id.value, "member") }
                .also { serverService.removeMemberToServer(it.id.value, "member") }
            serverService.getServersFromUser("member").size shouldBe 0
        }

        "should let user be kicked from server" {
            serverService
                .createServer(CreateServerRequest("name", "description", "owner"))
                .also { serverService.addMemberToServer(it.id.value, "member") }
                .also { serverService.kickUserFromServer(it.id.value, "member") }
            serverService.getServersFromUser("member").size shouldBe 0
        }

        "should not let user join server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.addMemberToServer(MOCK_NOT_EXISTING_SERVER_ID, "member")
            }
        }

        "should not let user leave server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.removeMemberToServer(MOCK_NOT_EXISTING_SERVER_ID, "member")
            }
        }

        "should not let user be kicked from server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.kickUserFromServer(MOCK_NOT_EXISTING_SERVER_ID, "member")
            }
        }
    })
