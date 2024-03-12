package piperkt.services.servers.infrastructure

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.ChannelType

@MicronautTest
class ChannelRepositoryImplTest(
    private val channelRepositoryImpl: ChannelRepository,
    private val serverRepositoryImpl: ServerRepository
) : AnnotationSpec() {
    private var serverId: ServerId? = null

    @BeforeEach
    fun setUp() {
        serverId = serverRepositoryImpl.save("serverName", "serverDescription", "serverOwner")?.id
    }

    @Test
    fun `should create a new channel`() {
        val channel =
            channelRepositoryImpl.save(serverId!!, "channelName", "channelDescription", "TEXT")
        channel shouldNotBe null
        channel!!.channelId shouldNotBe ""
        channel.name shouldBe "channelName"
        channel.description shouldBe "channelDescription"
        channel.type shouldBe ChannelType.TEXT
    }
}
