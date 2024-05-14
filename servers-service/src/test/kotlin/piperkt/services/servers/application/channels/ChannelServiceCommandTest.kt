package piperkt.services.servers.application.channels

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.common.events.ChannelEvent
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.exceptions.ServerServiceException

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
                name = "channelName",
                description = "channelDescription",
                type = "TEXT",
                requestFrom = "owner"
            )
        val response = channelService.createNewChannelInServer(request)
        response.isSuccess shouldBe true
        // Only way to test the response is to check if the event was published with the correct
        // channelId
        verify(eventPublisher)
            .publish(
                ChannelEvent.ChannelCreatedEvent(
                    simpleServerId.value,
                    response.getOrThrow().channelId.value
                )
            )
    }

    @Test
    fun `should not allow to create a channel if server doesn't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(null)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                serverId = simpleServerId,
                name = "channelName",
                description = "channelDescription",
                type = "TEXT",
                requestFrom = "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to create a channel if user isn't the owner`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                serverId = simpleServerId,
                name = "channelName",
                description = "channelDescription",
                type = "TEXT",
                requestFrom = "notOwner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a channel`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                newName = "newChannelName",
                newDescription = "channelDescription",
                requestFrom = "owner"
            )
        ) shouldBe
            Result.success(
                ChannelCommand.UpdateChannelInServer.Response(
                    channelId = simpleChannelId,
                    newName = "newChannelName",
                    newDescription = "channelDescription"
                )
            )
        verify(eventPublisher)
            .publish(ChannelEvent.ChannelUpdatedEvent(simpleServerId.value, simpleChannelId.value))
    }

    @Test
    fun `should not allow to update a channel if channel doesn't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                newName = "channelName",
                newDescription = "channelDescription",
                requestFrom = "owner"
            )
        ) shouldBe Result.failure(ServerServiceException.ChannelNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a channel if user isn't the owner`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServerWithChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                newName = "channelName",
                newDescription = "channelDescription",
                requestFrom = "notOwner"
            )
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
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
        ) shouldBe
            Result.success(
                ChannelCommand.DeleteChannelInServer.Response(channelId = simpleChannelId)
            )
        verify(eventPublisher)
            .publish(ChannelEvent.ChannelDeletedEvent(simpleServerId.value, simpleChannelId.value))
    }

    @Test
    fun `should not allow to delete a channel if channel doesn't exist`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                serverId = simpleServerId,
                channelId = simpleChannelId,
                requestFrom = "owner"
            )
        ) shouldBe Result.failure(ServerServiceException.ChannelNotFoundException())
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
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
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
                    requestFrom = "sender"
                )
            )
        response.isSuccess shouldBe true
        verify(eventPublisher)
            .publish(
                ChannelEvent.MessageInChannelEvent(
                    simpleServerId.value,
                    simpleChannelId.value,
                    response.getOrThrow().messageId.value
                )
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
                requestFrom = "sender"
            )
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }
}
