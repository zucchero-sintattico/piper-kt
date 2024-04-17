package piperkt.services.friendships.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.exceptions.FriendshipServiceException
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

class FriendshipServiceTest : AnnotationSpec() {

    private val mockedRepository: FriendshipAggregateRepository = mock()
    private val service = FriendshipService(mockedRepository)

    @BeforeEach
    fun setup() {
        reset(mockedRepository)
    }

    @Test
    fun `should allow to send friend request`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should not allow to send friend request if user is not the sender`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, "notPeppe")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to send friend request if request already exists`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        whenever(mockedRepository.findFriendshipRequest(request.from, request.to))
            .thenReturn(FriendshipRequestFactory.createFriendshipRequest(request.from, request.to))
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }
}
