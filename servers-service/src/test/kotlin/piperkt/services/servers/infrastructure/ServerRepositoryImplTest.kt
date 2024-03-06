package piperkt.services.servers.infrastructure

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.servers.domain.ServerRepository

const val MOCK_NOT_EXISTING_SERVER_ID = "12345678901d345678901234"

@MicronautTest
class ServerRepositoryImplTest(private val serverRepository: ServerRepository) :
    StringSpec({
        "should create a new server" {
            serverRepository.save("serverName", "serverDescription", "owner").should {
                it.id.value shouldNotBe ""
                it.name shouldBe "serverName"
                it.description shouldBe "serverDescription"
                it.owner shouldBe "owner"
                it.members shouldBe listOf("owner")
                it.channels shouldBe emptyList()
            }
        }

        "should delete a server" {
            serverRepository.save("serverName", "serverDescription", "owner").also {
                serverRepository.deleteServer(it.id.value)
            }
            serverRepository.findByMember("owner").size shouldBe 0
        }

        "should update a server" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository
                    .updateServer(serverCreated.id.value, "newServerName", "newServerDescription")
                    .should {
                        it shouldNotBe null
                        it!!.id.value shouldBe serverCreated.id.value
                        it.name shouldBe "newServerName"
                        it.description shouldBe "newServerDescription"
                        it.owner shouldBe "owner"
                        it.members shouldBe listOf("owner")
                        it.channels shouldBe emptyList()
                    }
            }
        }

        "should get server members" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.getServerMembers(serverCreated.id.value) shouldBe listOf("owner")
            }
        }

        "should add server member" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.addServerMember(serverCreated.id.value, "member").should {
                    it shouldNotBe null
                    it!!.id.value shouldBe serverCreated.id.value
                    it.name shouldBe "serverName"
                    it.description shouldBe "serverDescription"
                    it.owner shouldBe "owner"
                    it.members shouldBe listOf("owner", "member")
                    it.channels shouldBe emptyList()
                }
            }
        }

        "should remove server member" {
            serverRepository.save("serverName", "serverDescription", "owner").also { serverCreated
                ->
                serverRepository.addServerMember(serverCreated.id.value, "member").also {
                    serverWithMember ->
                    serverRepository
                        .removeServerMember(serverWithMember!!.id.value, "member")
                        .should {
                            it shouldNotBe null
                            it!!.id.value shouldBe it.id.value
                            it.name shouldBe "serverName"
                            it.description shouldBe "serverDescription"
                            it.owner shouldBe "owner"
                            it.members shouldBe listOf("owner")
                            it.channels shouldBe emptyList()
                        }
                }
            }
        }

        "should return null when server not found on updateServer" {
            serverRepository.updateServer(
                MOCK_NOT_EXISTING_SERVER_ID,
                "newServerName",
                "newServerDescription"
            ) shouldBe null
        }

        "should return empty list when server not found on getServerMembers" {
            serverRepository.getServerMembers(MOCK_NOT_EXISTING_SERVER_ID) shouldBe emptyList()
        }

        "should return null when server not found on addServerMember" {
            serverRepository.addServerMember(MOCK_NOT_EXISTING_SERVER_ID, "member") shouldBe null
        }

        "should return null when server not found on removeServerMember" {
            serverRepository.removeServerMember(MOCK_NOT_EXISTING_SERVER_ID, "member") shouldBe null
        }

        "should return empty list when server not found on findByMember" {
            serverRepository.findByMember("member") shouldBe emptyList()
        }
    })
