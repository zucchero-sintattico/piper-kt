package piperkt.services.servers.application.channels

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.events.ChannelEvent
import piperkt.services.servers.application.SimpleClasses.simpleChannel
import piperkt.services.servers.application.SimpleClasses.simpleChannelId
import piperkt.services.servers.application.SimpleClasses.simpleMessage
import piperkt.services.servers.application.SimpleClasses.simpleServer
import piperkt.services.servers.application.SimpleClasses.simpleServerId
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelServiceCommandTest : BasicChannelServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(channelRepository, serverRepository, eventPublisher)
    }

    @Test
    fun `should allow to create a channel if server exist`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(simpleChannel())
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                simpleServerId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.success(ChannelCommand.CreateNewChannelInServer.Response(simpleChannelId()))
        verify(eventPublisher).publish(ChannelEvent.ChannelCreatedEvent(simpleChannelId()))
    }

    @Test
    fun `should not allow to create a channel if server doesn't exist`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(null)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                simpleServerId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(ServerOrChannelNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to create a channel if user isn't the owner`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(simpleChannel())
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                simpleServerId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "notOwner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a channel`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any()))
            .thenReturn(simpleChannel())
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        ) shouldBe Result.success(ChannelCommand.UpdateChannelInServer.Response)
        verify(eventPublisher).publish(ChannelEvent.ChannelUpdatedEvent(simpleChannelId()))
    }

    @Test
    fun `should not allow to update a channel if server or channel don't exist`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any())).thenReturn(null)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a channel if user isn't the owner`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any()))
            .thenReturn(simpleChannel())
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "channelName",
                "channelDescription",
                "TEXT",
                "notOwner"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to delete a channel`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "owner"
            )
        ) shouldBe Result.success(ChannelCommand.DeleteChannelInServer.Response)
        verify(eventPublisher).publish(ChannelEvent.ChannelDeletedEvent(simpleChannelId()))
    }

    @Test
    fun `should not allow to delete a channel if server or channel don't exist`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(false)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "owner"
            )
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to delete a channel if user isn't the owner`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(true)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(
                simpleServerId(),
                simpleChannelId(),
                "notOwner"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    // Messages
    @Test
    fun `should allow to add a message in a channel`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(channelRepository.addMessageInChannel(any(), any(), any(), any()))
            .thenReturn(simpleMessage())
        channelService.addMessageInChannel(
            ChannelCommand.AddMessageInChannel.Request(
                simpleServerId(),
                simpleChannelId(),
                "content",
                "sender"
            )
        ) shouldBe Result.success(ChannelCommand.AddMessageInChannel.Response)
        verify(eventPublisher)
            .publish(
                ChannelEvent.MessageInChannelEvent(simpleChannelId(), simpleMessage().messageId)
            )
    }

    @Test
    fun `should not allow to add a message in a channel if user is not in server`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(false)
        channelService.addMessageInChannel(
            ChannelCommand.AddMessageInChannel.Request(
                simpleServerId(),
                simpleChannelId(),
                "content",
                "sender"
            )
        ) shouldBe Result.failure(UserNotInServerException())
        verifyNoInteractions(eventPublisher)
    }
}
