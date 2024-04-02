package piperkt.services.servers.application.channels

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import piperkt.services.servers.application.SimpleClasses.simpleChannelId
import piperkt.services.servers.application.SimpleClasses.simpleMessage
import piperkt.services.servers.application.SimpleClasses.simpleServerId
import piperkt.services.servers.application.SimpleClasses.simpleServerWithChannelAndMessage
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelServiceQueryTest : BasicChannelServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should get messages from channel if user is in server`() {
        val fakeMessages = listOf(simpleMessage())
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannelAndMessage())
        val response =
            channelService.getMessagesFromChannelId(
                ChannelQuery.GetMessagesFromChannelId.Request(
                    simpleServerId(),
                    simpleChannelId(),
                    0,
                    10,
                    "requestFrom"
                )
            )
        response shouldBe
            Result.success(ChannelQuery.GetMessagesFromChannelId.Response(fakeMessages))
        response.getOrNull()?.messages shouldBe fakeMessages
    }

    @Test
    fun `should not get messages from channel if user is not in server`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(false)
        channelService.getMessagesFromChannelId(
            ChannelQuery.GetMessagesFromChannelId.Request(
                simpleServerId(),
                simpleChannelId(),
                0,
                10,
                "requestFrom"
            )
        ) shouldBe Result.failure(UserNotInServerException())
    }
}
