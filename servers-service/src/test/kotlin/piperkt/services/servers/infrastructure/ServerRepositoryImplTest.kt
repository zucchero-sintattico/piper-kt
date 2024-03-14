package piperkt.services.servers.infrastructure

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerRepository

val NOT_EXISTING_SERVER_ID = ServerId("12345678901d345678901234")

@MicronautTest
class ServerRepositoryImplTest(private val serverRepository: ServerRepository) :
    StringSpec({
        "should create a new server" {
            serverRepository.save("serverName", "serverDescription", "owner").should {
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
                serverRepository.deleteServer(it.id)
            }
            serverRepository.getServersFromUser("owner").size shouldBe 0
        }

        "should update a server" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository
                    .updateServer(serverCreated.id, "newServerName", "newServerDescription")
                    .should {
                        it shouldNotBe null
                        it!!.id.value shouldBe serverCreated.id.value
                        it.name shouldBe "newServerName"
                        it.description shouldBe "newServerDescription"
                        it.owner shouldBe "owner"
                        it.users shouldBe listOf("owner")
                        it.channels shouldBe emptyList()
                    }
            }
        }

        "should get server members" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.getServerUsers(serverCreated.id) shouldBe listOf("owner")
            }
        }

        "should add server member" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.addUserToServer(serverCreated.id, "member").should {
                    it shouldNotBe null
                    it!!.id.value shouldBe serverCreated.id.value
                    it.name shouldBe "serverName"
                    it.description shouldBe "serverDescription"
                    it.owner shouldBe "owner"
                    it.users shouldBe listOf("owner", "member")
                    it.channels shouldBe emptyList()
                }
            }
        }

        "should remove server member" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.addUserToServer(serverCreated.id, "member").also { serverWithMember
                    ->
                    serverRepository.removeUserFromServer(serverWithMember!!.id, "member").should {
                        it shouldNotBe null
                        it!!.id.value shouldBe it.id.value
                        it.name shouldBe "serverName"
                        it.description shouldBe "serverDescription"
                        it.owner shouldBe "owner"
                        it.users shouldBe listOf("owner")
                        it.channels shouldBe emptyList()
                    }
                }
            }
        }

        "should return null when server not found on updateServer" {
            serverRepository.updateServer(
                NOT_EXISTING_SERVER_ID,
                "newServerName",
                "newServerDescription"
            ) shouldBe null
        }

        "should return empty list when server not found on getServerMembers" {
            serverRepository.getServerUsers(NOT_EXISTING_SERVER_ID) shouldBe emptyList()
        }

        "should return null when server not found on addServerMember" {
            serverRepository.addUserToServer(NOT_EXISTING_SERVER_ID, "member") shouldBe null
        }

        "should return null when server not found on removeServerMember" {
            serverRepository.removeUserFromServer(NOT_EXISTING_SERVER_ID, "member") shouldBe null
        }

        "should return empty list when server not found on findByMember" {
            serverRepository.getServersFromUser("member") shouldBe emptyList()
        }
    })
