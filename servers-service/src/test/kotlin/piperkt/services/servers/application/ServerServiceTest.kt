package piperkt.services.servers.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.command.AddUserToServerRequest
import piperkt.services.servers.application.api.command.CreateServerRequest
import piperkt.services.servers.application.api.command.DeleteServerRequest
import piperkt.services.servers.application.api.command.KickUserFromServerRequest
import piperkt.services.servers.application.api.command.RemoveUserFromServerRequest
import piperkt.services.servers.application.api.command.UpdateServerRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserResponse
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.domain.factory.ServerFactory

class ServerServiceTest : AnnotationSpec() {
    private val mockedRepository = mock<ServerRepository>()
    private val serverService = ServerService(mockedRepository)
    private val fakeServerId = ServerId("000000000000000000000000")
    private val fakeServer =
        ServerFactory.createServer(
            fakeServerId.value,
            "serverName",
            "serverDescription",
            "serverOwner"
        )

    @Test
    fun `should allow to create a server`() {
        whenever(mockedRepository.save(any(), any(), any())).thenReturn(fakeServer)
        val request =
            CreateServerRequest("serverName", "serverDescription", "serverOwner", "serverOwner")
        serverService.createServer(request) shouldBe Result.success(Unit)
    }

    @Test
    fun `should not allow to update a server if is not the admin`() {
        whenever(mockedRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        serverService.updateServer(
            UpdateServerRequest(fakeServerId, "serverName", "serverDescription", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should allow to delete a server`() {
        whenever(mockedRepository.deleteServer(any())).thenReturn(true)
        whenever(mockedRepository.findById(any())).thenReturn(fakeServer)
        serverService.deleteServer(DeleteServerRequest(fakeServerId, "serverOwner")) shouldBe
            Result.success(Unit)
    }

    @Test
    fun `should not allow to delete a server that does not exist`() {
        whenever(mockedRepository.deleteServer(any())).thenReturn(false)
        serverService.deleteServer(DeleteServerRequest(fakeServerId, "serverOwner")) shouldBe
            Result.failure(ServerOrChannelNotFoundException())
    }

    @Test
    fun `should not allow to delete a server if user doesn't have permission`() {
        whenever(mockedRepository.deleteServer(any())).thenReturn(true)
        whenever(mockedRepository.findById(any())).thenReturn(fakeServer)
        serverService.deleteServer(DeleteServerRequest(fakeServerId, "member")) shouldBe
            Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to update a server that does not exist`() {
        whenever(mockedRepository.updateServer(any(), any(), any())).thenReturn(null)
        serverService.updateServer(
            UpdateServerRequest(fakeServerId, "serverName", "serverDescription", "serverOwner")
        ) shouldBe Result.failure(ServerOrChannelNotFoundException())
    }

    @Test
    fun `should not allow to update a server if user doesn't have permission`() {
        whenever(mockedRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        serverService.updateServer(
            UpdateServerRequest(fakeServerId, "serverName", "serverDescription", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should allow to update a server`() {
        whenever(mockedRepository.updateServer(any(), any(), any())).thenReturn(fakeServer)
        serverService.updateServer(
            UpdateServerRequest(fakeServerId, "serverName", "serverDescription", "serverOwner")
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should allow user to join the server`() {
        whenever(mockedRepository.addUserToServer(any(), any())).thenReturn(fakeServer)
        serverService.addUserToServer(
            AddUserToServerRequest(fakeServerId, "member", "member")
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should not allow to join a server that does not exist`() {
        whenever(mockedRepository.addUserToServer(any(), any())).thenReturn(null)
        serverService.addUserToServer(
            AddUserToServerRequest(fakeServerId, "member", "member")
        ) shouldBe Result.failure(ServerOrChannelNotFoundException())
    }

    @Test
    fun `should allow user to leave the server`() {
        whenever(mockedRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        serverService.removeUserFromServer(
            RemoveUserFromServerRequest(fakeServerId, "member", "member")
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should allow the admin to kick a user`() {
        whenever(mockedRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        serverService.kickUserFromServer(
            KickUserFromServerRequest(fakeServerId, "member", "serverOwner")
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should not allow non-admin to kick a user`() {
        whenever(mockedRepository.removeUserFromServer(any(), any())).thenReturn(fakeServer)
        serverService.kickUserFromServer(
            KickUserFromServerRequest(fakeServerId, "member", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }

    @Test
    fun `should allow to get servers from user without servers`() {
        whenever(mockedRepository.getServersFromUser(any())).thenReturn(emptyList())
        serverService.getServersFromUser(GetServersFromUserRequest("username", "username")) shouldBe
            Result.success(GetServersFromUserResponse(emptyList()))
    }

    @Test
    fun `should allow to get servers from user with servers`() {
        whenever(mockedRepository.getServersFromUser(any())).thenReturn(listOf(fakeServer))
        serverService.getServersFromUser(GetServersFromUserRequest("username", "username")) shouldBe
            Result.success(GetServersFromUserResponse(listOf(fakeServer)))
    }

    @Test
    fun `should not allow to get servers from a user that isn't making the request`() {
        serverService.getServersFromUser(GetServersFromUserRequest("username", "member")) shouldBe
            Result.failure(UserNotHasPermissionsException())
    }
}
