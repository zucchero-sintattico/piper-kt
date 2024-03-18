package piperkt.services.servers.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.commons.domain.events.ServerEvent
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.application.exceptions.ServerNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.domain.factory.ServerFactory

class ServerServiceTest : AnnotationSpec() {
    private val serverRepository = mock<ServerRepository>()
    private val eventPublisher = mock<DomainEventPublisher>()
    private val serverService = ServerService(serverRepository, eventPublisher)
    private val fakeServerId = ServerId("000000000000000000000000")
    private val fakeServer =
        ServerFactory.createServer(
            fakeServerId.value,
            "serverName",
            "serverDescription",
            "serverOwner"
        )

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should allow to create a server`() {
        whenever(serverRepository.save(any(), any(), any())).thenReturn(fakeServer)
        val request =
            ServerCommand.CreateServer.Request(
                "serverName",
                "serverDescription",
                "serverOwner",
                "serverOwner"
            )
        serverService.createServer(request) shouldBe
            Result.success(ServerCommand.CreateServer.Response(fakeServerId))
        verify(eventPublisher).publish(ServerEvent.ServerCreatedEvent(fakeServerId))
    }

    @Test
    fun `should not allow to update a server if is not the admin`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                fakeServerId,
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
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(fakeServerId, "serverOwner")
        ) shouldBe Result.success(ServerCommand.DeleteServer.Response)

        verify(eventPublisher).publish(ServerEvent.ServerDeletedEvent(fakeServerId))
    }

    @Test
    fun `should not allow to delete a server that does not exist`() {
        whenever(serverRepository.deleteServer(any())).thenReturn(false)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(fakeServerId, "serverOwner")
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to delete a server if user doesn't have permission`() {
        whenever(serverRepository.deleteServer(any())).thenReturn(true)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.deleteServer(
            ServerCommand.DeleteServer.Request(fakeServerId, "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server that does not exist`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(null)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                fakeServerId,
                "serverName",
                "serverDescription",
                "serverOwner"
            )
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should not allow to update a server if user doesn't have permission`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                fakeServerId,
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to update a server`() {
        whenever(serverRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                fakeServerId,
                "serverName",
                "serverDescription",
                fakeServer.owner
            )
        ) shouldBe Result.success(ServerCommand.UpdateServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUpdatedEvent(fakeServerId))
    }

    @Test
    fun `should allow user to join the server`() {
        whenever(serverRepository.addUserToServer(any(), any())).thenReturn(fakeServer)
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(fakeServerId, "member", "member")
        ) shouldBe Result.success(ServerCommand.AddUserToServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUserAddedEvent(fakeServerId, "member"))
    }

    @Test
    fun `should not allow to join a server that does not exist`() {
        whenever(serverRepository.addUserToServer(any(), any())).thenReturn(null)
        serverService.addUserToServer(
            ServerCommand.AddUserToServer.Request(fakeServerId, "member", "member")
        ) shouldBe Result.failure(ServerNotFoundException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow user to leave the server`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        serverService.removeUserFromServer(
            ServerCommand.RemoveUserFromServer.Request(fakeServerId, "member", "member")
        ) shouldBe Result.success(ServerCommand.RemoveUserFromServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUserRemovedEvent(fakeServerId, "member"))
    }

    @Test
    fun `should allow the admin to kick a user`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        whenever(serverRepository.findById(any())).thenReturn(fakeServer)
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(fakeServerId, "member", "serverOwner")
        ) shouldBe Result.success(ServerCommand.KickUserFromServer.Response)
        verify(eventPublisher).publish(ServerEvent.ServerUserRemovedEvent(fakeServerId, "member"))
    }

    @Test
    fun `should not allow non-admin to kick a user`() {
        whenever(serverRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        serverService.kickUserFromServer(
            ServerCommand.KickUserFromServer.Request(fakeServerId, "member", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
        verifyNoInteractions(eventPublisher)
    }

    @Test
    fun `should allow to get servers from user without servers`() {
        whenever(serverRepository.getServersFromUser(any())).thenReturn(emptyList())
        serverService.getServersFromUser(
            ServerQuery.GetServersFromUser.Request("username", "username")
        ) shouldBe Result.success(ServerQuery.GetServersFromUser.Response(emptyList()))
    }

    @Test
    fun `should allow to get servers from user with servers`() {
        whenever(serverRepository.getServersFromUser(any())).thenReturn(listOf(fakeServer))
        serverService.getServersFromUser(
            ServerQuery.GetServersFromUser.Request("username", "username")
        ) shouldBe Result.success(ServerQuery.GetServersFromUser.Response(listOf(fakeServer)))
    }

    @Test
    fun `should not allow to get servers from a user that isn't making the request`() {
        serverService.getServersFromUser(
            ServerQuery.GetServersFromUser.Request("username", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }
}
