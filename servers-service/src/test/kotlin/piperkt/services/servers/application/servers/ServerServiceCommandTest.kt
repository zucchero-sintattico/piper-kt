package piperkt.services.servers.application.servers

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.events.*
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.exceptions.ServerServiceException

class ServerServiceCommandTest : BasicServerServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should allow to create a server`() {
        val request =
            ServerCommand.CreateServer.Request(
                "serverName",
                "serverDescription",
                "serverOwner",
            )
        val response = serverService.createServer(request)
        response.isSuccess shouldBe true
        verify(serverRepository).save(any())
        verify(eventPublisher)
            .publish(ServerCreatedEvent(response.getOrThrow().serverId.value, "serverOwner"))
    }

    @Test
    fun `should not allow to update a server if is not the admin`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId,
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to delete a server`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId, "owner")
        ) shouldBe Result.success(ServerCommand.DeleteServer.Response(simpleServerId))

        verify(eventPublisher).publish(ServerDeletedEvent(simpleServerId.value))
    }

    @Test
    fun `should not allow to delete a server that does not exist`() {
        whenever(serverRepository.findById(any())).thenReturn(null)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId, "owner")
        ) shouldBe Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to delete a server if user doesn't have permission`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(simpleServerId, "member")
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server that does not exist`() {
        whenever(serverRepository.findById(any())).thenReturn(null)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId,
                "serverName",
                "serverDescription",
                "owner"
            )
        ) shouldBe Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server if user doesn't have permission`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId,
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a server`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId,
                "serverName",
                "serverDescription",
                simpleServer.owner
            )
        ) shouldBe
            Result.success(
                ServerCommand.UpdateServer.Response(
                    simpleServerId,
                    "serverName",
                    "serverDescription"
                )
            )
        verify(eventPublisher).publish(ServerUpdatedEvent(simpleServerId.value))
    }

    @Test
    fun `should allow user to join the server`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId, "member")
        ) shouldBe Result.success(ServerCommand.AddUserToServer.Response(simpleServerId, "member"))
        verify(eventPublisher).publish(ServerUserAddedEvent(simpleServerId.value, "member"))
    }

    @Test
    fun `should not allow to join a server that does not exist`() {
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId, "member")
        ) shouldBe Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow user to leave the server`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId, "member")
        ) shouldBe Result.success(ServerCommand.AddUserToServer.Response(simpleServerId, "member"))
        serverService.removeUserFromServer(
            ServerCommand.RemoveUserFromServer.Request(simpleServerId, "member")
        ) shouldBe
            Result.success(ServerCommand.RemoveUserFromServer.Response(simpleServerId, "member"))
        verify(eventPublisher).publish(ServerUserRemovedEvent(simpleServerId.value, "member"))
    }

    @Test
    fun `should not allow the owner to leave the server`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.removeUserFromServer(
            ServerCommand.RemoveUserFromServer.Request(simpleServerId, "owner")
        ) shouldBe Result.failure(ServerServiceException.OwnerCannotLeaveServerException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow the admin to kick a user`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        // Add user to server
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(simpleServerId, "member")
        ) shouldBe Result.success(ServerCommand.AddUserToServer.Response(simpleServerId, "member"))
        // And then kick the user
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(simpleServerId, "member", "owner")
        ) shouldBe
            Result.success(ServerCommand.KickUserFromServer.Response(simpleServerId, "member"))
        verify(eventPublisher).publish(ServerUserKickedEvent(simpleServerId.value, "member"))
    }

    @Test
    fun `should not allow non-admin to kick a user`() {
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(simpleServerId, "member", "member")
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }
}
