package piperkt.services.servers.application.servers

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.events.ServerEvent
import piperkt.services.servers.application.SimpleClasses.simpleServer
import piperkt.services.servers.application.SimpleClasses.simpleServerId
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.exceptions.ServerNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException

class ServerServiceCommandTest : BasicServerServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should allow to create a server`() {
        whenever(serverRepository.save(any(), any(), any())).thenReturn(simpleServer())
        val request =
            ServerCommand.CreateServer.Request(
                "serverName",
                "serverDescription",
                "serverOwner",
                "serverOwner"
            )
        serverService.createServer(request) shouldBe
            Result.success(ServerCommand.CreateServer.Response(simpleServerId()))
        verify(eventPublisher).publish(ServerEvent.ServerCreatedEvent(simpleServerId()))
    }

    @Test
    fun `should not allow to update a server if is not the admin`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(simpleServer())
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId(),
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to delete a server`() {
        whenever(serverRepository.deleteServer(any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId(), "owner")
        ) shouldBe Result.success(ServerCommand.DeleteServer.Response)

        verify(eventPublisher).publish(ServerEvent.ServerDeletedEvent(simpleServerId()))
    }

    @Test
    fun `should not allow to delete a server that does not exist`() {
        whenever(serverRepository.deleteServer(any())).thenReturn(false)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId(), "owner")
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to delete a server if user doesn't have permission`() {
        whenever(serverRepository.deleteServer(any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId(), "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server that does not exist`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(null)
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId(),
                "serverName",
                "serverDescription",
                "owner"
            )
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server if user doesn't have permission`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(simpleServer())
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId(),
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a server`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(simpleServer())
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId(),
                "serverName",
                "serverDescription",
                simpleServer().owner
            )
        ) shouldBe Result.success(ServerCommand.UpdateServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUpdatedEvent(simpleServerId()))
    }

    @Test
    fun `should allow user to join the server`() {
        whenever(serverRepository.addUserToServer(any(), any())).thenReturn(simpleServer())
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId(), "member", "member")
        ) shouldBe Result.success(ServerCommand.AddUserToServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUserAddedEvent(simpleServerId(), "member"))
    }

    @Test
    fun `should not allow to join a server that does not exist`() {
        whenever(serverRepository.addUserToServer(any(), any())).thenReturn(null)
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId(), "member", "member")
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow user to leave the server`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(simpleServer())
        serverService.removeUserFromServer(
            ServerCommand.RemoveUserFromServer.Request(simpleServerId(), "member", "member")
        ) shouldBe Result.success(ServerCommand.RemoveUserFromServer.Response)
        verify(eventPublisher)
            .publish(ServerEvent.ServerUserRemovedEvent(simpleServerId(), "member"))
    }

    @Test
    fun `should allow the admin to kick a user`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(simpleServer())
        whenever(serverRepository.findById(any())).thenReturn(simpleServer())
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(simpleServerId(), "member", "owner")
        ) shouldBe Result.success(ServerCommand.KickUserFromServer.Response)
        verify(eventPublisher)
            .publish(ServerEvent.ServerUserKickedEvent(simpleServerId(), "member"))
    }

    @Test
    fun `should not allow non-admin to kick a user`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(simpleServer())
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(simpleServerId(), "member", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }
}