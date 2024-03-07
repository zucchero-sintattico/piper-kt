package piperkt.services.servers.infrastructure

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.commons.domain.id.UserId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.exception.ServerNotFoundException
import piperkt.services.servers.application.request.AddMemberToServerRequest
import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.application.request.DeleteServerRequest
import piperkt.services.servers.application.request.GetServerFromUserRequest
import piperkt.services.servers.application.request.KickUserFromServerRequest
import piperkt.services.servers.application.request.RemoveMemberToServerRequest

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
            serverService
                .getServersFromUser(GetServerFromUserRequest(UserId("owner")))
                .size shouldBe 1
        }

        "should delete server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.deleteServer(DeleteServerRequest(it.id))
            }
            serverService
                .getServersFromUser(GetServerFromUserRequest(UserId("owner")))
                .size shouldBe 0
        }

        "should let user join server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.addMemberToServer(AddMemberToServerRequest(it.id, UserId("member")))
            }
            serverService
                .getServersFromUser(GetServerFromUserRequest(UserId("member")))
                .size shouldBe 1
        }

        "should let user leave server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.addMemberToServer(AddMemberToServerRequest(it.id, UserId("member")))
                serverService.removeMemberToServer(
                    RemoveMemberToServerRequest(it.id, UserId("member"))
                )
            }
            serverService
                .getServersFromUser(GetServerFromUserRequest(UserId("member")))
                .size shouldBe 0
        }

        "should let user be kicked from server" {
            serverService.createServer(CreateServerRequest("name", "description", "owner")).also {
                serverService.addMemberToServer(AddMemberToServerRequest(it.id, UserId("member")))
                serverService.kickUserFromServer(KickUserFromServerRequest(it.id, UserId("member")))
            }
            serverService
                .getServersFromUser(GetServerFromUserRequest(UserId("member")))
                .size shouldBe 0
        }

        "should not let user join server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.addMemberToServer(
                    AddMemberToServerRequest(
                        ServerId(MOCK_NOT_EXISTING_SERVER_ID),
                        UserId("member")
                    )
                )
            }
        }

        "should not let user leave server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.removeMemberToServer(
                    RemoveMemberToServerRequest(
                        ServerId(MOCK_NOT_EXISTING_SERVER_ID),
                        UserId("member")
                    )
                )
            }
        }

        "should not let user be kicked from server if server does not exist" {
            shouldThrow<ServerNotFoundException> {
                serverService.kickUserFromServer(
                    KickUserFromServerRequest(
                        ServerId(MOCK_NOT_EXISTING_SERVER_ID),
                        UserId("member")
                    )
                )
            }
        }
    })
