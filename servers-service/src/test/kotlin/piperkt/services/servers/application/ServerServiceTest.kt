package piperkt.services.servers.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateServerRequest
import piperkt.services.servers.application.api.query.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.GetServersFromUserResponse
import piperkt.services.servers.domain.ServerRepository

class ServerServiceTest : AnnotationSpec() {
    private val mockedRepository = mock<ServerRepository>()
    private val serverService = ServerService(mockedRepository)

    @Test
    fun `should allow to create a server`() {
        val request = CreateServerRequest("serverName", "serverDescription", "serverOwner")
        serverService.createServer(request) shouldBe CommandResponse(true)
        verify(mockedRepository).save("serverName", "serverDescription", "serverOwner")
    }

    @Test
    fun `should allow to get servers from user without servers`() {
        val username = "username"
        serverService.getServersFromUser(GetServersFromUserRequest(username)) shouldBe
            GetServersFromUserResponse(emptyList())
        verify(mockedRepository).getServersFromUser(any())
    }
}
