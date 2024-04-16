package piperkt.services.servers.application.channels

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.common.events.ChannelEvent
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelServiceCommandTest : BasicChannelServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should allow to create a channel if server exist`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                serverId = simpleServerId,
                channelName = "channelName",
                channelDescription = "channelDescription",
                channelType = "TEXT",
                requestFrom = "owner"
            )
        val response = channelService.createNewChannelInServer(request)
        response.isSuccess shouldBe true
        // Only way to test the response is to check if the event was published with the correct
        // channelId
        verify(eventPublisher)
            .publish(ChannelEvent.ChannelCreatedEvent(response.getOrThrow().channelId))
    }

    @Test
    fun `should not allow to create a channel if server doesn't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(null)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                serverId = simpleServerId,
                channelName = "channelName",
                channelDescription = "channelDescription",
                channelType = "TEXT",
                requestFrom = "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(ServerOrChannelNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to create a channel if user isn't the owner`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                serverId = simpleServerId,
                channelName = "channelName",
                channelDescription = "channelDescription",
                channelType = "TEXT",
                requestFrom = "notOwner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a channel`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                channelName = "newChannelName",
                channelDescription = "channelDescription",
                requestFrom = "owner"
            )
        ) shouldBe Result.success(ChannelCommand.UpdateChannelInServer.Response)
        verify(eventPublisher).publish(ChannelEvent.ChannelUpdatedEvent(simpleChannelId))
    }

    @Test
    fun `should not allow to update a channel if server or channel don't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                channelName = "channelName",
                channelDescription = "channelDescription",
                requestFrom = "owner"
            )
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a channel if user isn't the owner`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                channelName = "channelName",
                channelDescription = "channelDescription",
                requestFrom = "notOwner"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to delete a channel`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                requestFrom = "owner"
            )
        ) shouldBe Result.success(ChannelCommand.DeleteChannelInServer.Response)
        verify(eventPublisher).publish(ChannelEvent.ChannelDeletedEvent(simpleChannelId))
    }

    @Test
    fun `should not allow to delete a channel if server or channel don't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                requestFrom = "owner"
            )
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to delete a channel if user isn't the owner`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                requestFrom = "notOwner"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    // Messages
    @Test
    fun `should allow to add a message in a channel`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        val response =
            channelService.addMessageInChannel(
                ChannelCommand.AddMessageInChannel.Request(
                    serverId = simpleServerId,
                    channelId = simpleChannelId,
                    content = "content",
                    sender = "sender"
                )
            )
        response.isSuccess shouldBe true
        verify(eventPublisher)
            .publish(
                ChannelEvent.MessageInChannelEvent(simpleChannelId, response.getOrThrow().messageId)
            )
    }

    @Test
    fun `should not allow to add a message in a channel if user is not in server`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(false)
        channelService.addMessageInChannel(
            ChannelCommand.AddMessageInChannel.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                content = "content",
                sender = "sender"
            )
        ) shouldBe Result.failure(UserNotInServerException())
        verifyNoInteractions(eventPublisher)
    }
}
