package piperkt.services.servers.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

class ChannelServiceTest : AnnotationSpec() {
    private val channelRepository = mock<ChannelRepository>()
    private val serverRepository = mock<ServerRepository>()
    private val channelService = ChannelService(channelRepository, serverRepository)
    private val fakeServerId = ServerId("000000000000000000000000")
    private val fakeChannelId = ChannelId("000000000000000000000000")
    private val fakeServer =
        ServerFactory.createServer(fakeServerId.value, "serverName", "serverDescription", "owner")
    private val fakeChannel =
        ChannelFactory.createFromType(
            fakeChannelId.value,
            "channelName",
            "channelDescription",
            "TEXT"
        )

    @Test
    fun `should allow to create a channel if server exist`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(fakeChannel)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.success(ChannelCommand.CreateNewChannelInServer.Response(fakeChannelId))
    }

    @Test
    fun `should not allow to create a channel if server doesn't exist`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(null)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(ServerOrChannelNotFoundException())
    }

    @Test
    fun `should not allow to create a channel if user isn't the owner`() {
        whenever(channelRepository.createChannelInServer(any(), any(), any(), any()))
            .thenReturn(fakeChannel)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        val request =
            ChannelCommand.CreateNewChannelInServer.Request(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT",
                "notOwner"
            )
        channelService.createNewChannelInServer(request) shouldBe
            Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should allow to update a channel`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any()))
            .thenReturn(fakeChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                fakeServerId,
                fakeChannelId,
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        ) shouldBe Result.success(ChannelCommand.UpdateChannelInServer.Response)
    }

    @Test
    fun `should not allow to update a channel if server or channel don't exist`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any())).thenReturn(null)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                fakeServerId,
                fakeChannelId,
                "channelName",
                "channelDescription",
                "TEXT",
                "owner"
            )
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
    }

    @Test
    fun `should not allow to update a channel if user isn't the owner`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any()))
            .thenReturn(fakeChannel)
        channelService.updateChannelInServer(
            ChannelCommand.UpdateChannelInServer.Request(
                fakeServerId,
                fakeChannelId,
                "channelName",
                "channelDescription",
                "TEXT",
                "notOwner"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should allow to delete a channel`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(true)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(fakeServerId, fakeChannelId, "owner")
        ) shouldBe Result.success(ChannelCommand.DeleteChannelInServer.Response)
    }

    @Test
    fun `should not allow to delete a channel if server or channel don't exist`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(false)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(fakeServerId, fakeChannelId, "owner")
        ) shouldBe Result.failure(ServerOrChannelNotFoundException("Server or Channel not found"))
    }

    @Test
    fun `should not allow to delete a channel if user isn't the owner`() {
        whenever(channelRepository.deleteChannel(any(), any())).thenReturn(true)
        channelService.deleteChannelInServer(
            ChannelCommand.DeleteChannelInServer.Request(fakeServerId, fakeChannelId, "notOwner")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should get messages from channel if user is in server`() {
        val fakeMessages =
            listOf(MessageFactory.createMessage("000000000000000000000000", "content", "sender"))
        whenever(channelRepository.getMessagesFromServerIdAndChannelId(any(), any(), any()))
            .thenReturn(fakeMessages)
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(channelRepository.findChannelById(any())).thenReturn(fakeChannel)
        val response =
            channelService.getMessagesFromChannelId(
                ChannelQuery.GetMessagesFromChannelId.Request(
                    fakeServerId,
                    fakeChannelId,
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
                fakeServerId,
                fakeChannelId,
                0,
                10,
                "requestFrom"
            )
        ) shouldBe Result.failure(UserNotInServerException())
    }

    @Test
    fun `should allow to add a message in a channel`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(true)
        whenever(channelRepository.addMessageInChannel(any(), any(), any(), any())).thenReturn(true)
        channelService.addMessageInChannel(
            ChannelCommand.AddMessageInChannel.Request(
                fakeServerId,
                fakeChannelId,
                "content",
                "sender"
            )
        ) shouldBe Result.success(ChannelCommand.AddMessageInChannel.Response)
    }

    @Test
    fun `should not allow to add a message in a channel if user is not in server`() {
        whenever(serverRepository.isUserInServer(any(), any())).thenReturn(false)
        channelService.addMessageInChannel(
            ChannelCommand.AddMessageInChannel.Request(
                fakeServerId,
                fakeChannelId,
                "content",
                "sender"
            )
        ) shouldBe Result.failure(UserNotInServerException())
    }
}
