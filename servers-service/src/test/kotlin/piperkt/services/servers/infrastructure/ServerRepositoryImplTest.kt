package piperkt.services.servers.infrastructure

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.servers.application.ServerRepository

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
                serverRepository.delete(it.id)
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

            serverRepository.findByMember("owner").size shouldBe 1
        }
    })
