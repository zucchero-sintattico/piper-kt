package piperkt.services.servers.application.servers

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import piperkt.services.servers.application.SimpleClasses
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException

class ServerServiceQueryTest : BasicServerServiceTest() {
    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
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
        whenever(serverRepository.getServersFromUser(any()))
            .thenReturn(listOf(SimpleClasses.simpleServer()))
        serverService.getServersFromUser(
            ServerQuery.GetServersFromUser.Request("username", "username")
        ) shouldBe
            Result.success(
                ServerQuery.GetServersFromUser.Response(listOf(SimpleClasses.simpleServer()))
            )
    }

    @Test
    fun `should not allow to get servers from a user that isn't making the request`() {
        serverService.getServersFromUser(
            ServerQuery.GetServersFromUser.Request("username", "member")
        ) shouldBe Result.failure(UserNotHasPermissionsException())
    }
}
