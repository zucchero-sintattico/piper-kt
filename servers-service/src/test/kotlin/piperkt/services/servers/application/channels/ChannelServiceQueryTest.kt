package piperkt.services.servers.application.channels

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import piperkt.services.servers.application.SimpleClasses.fakeChannel
import piperkt.services.servers.application.SimpleClasses.fakeChannelId
import piperkt.services.servers.application.SimpleClasses.fakeMessage
import piperkt.services.servers.application.SimpleClasses.fakeServerId
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelServiceQueryTest : BasicChannelServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(channelRepository, serverRepository, eventPublisher)
    }

    @Test
    fun `should get messages from channel if user is in server`() {
        val fakeMessages = listOf(fakeMessage())
        whenever(channelRepository.getMessagesFromServerIdAndChannelId(any(), any(), any()))
            .thenReturn(fakeMessages)
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(channelRepository.findChannelById(any())).thenReturn(fakeChannel())
        val response =
            channelService.getMessagesFromChannelId(
                ChannelQuery.GetMessagesFromChannelId.Request(
                    fakeServerId(),
                    fakeChannelId(),
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
                fakeServerId(),
                fakeChannelId(),
                0,
                10,
                "requestFrom"
            )
        ) shouldBe Result.failure(UserNotInServerException())
    }
}
