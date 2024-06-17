package piperkt.services.friendships.application

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.Mockito.reset
import org.mockito.kotlin.mock
import piperkt.events.FriendshipEventPublisher
import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.domain.FriendshipRepository
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory
import piperkt.services.friendships.domain.toFriendship

open class BasicFriendshipServiceTest : AnnotationSpec() {

    protected val mockedFriendshipRequestRepository: FriendshipRequestRepository = mock()
    protected val mockedFriendshipRepository: FriendshipRepository = mock()
    protected val mockedEventPublisher: FriendshipEventPublisher = mock()
    protected val service =
        FriendshipService(
            friendshipRepository = mockedFriendshipRepository,
            friendshipRequestRepository = mockedFriendshipRequestRepository,
            eventPublisher = mockedEventPublisher
        )
    lateinit var request: FriendshipRequest
    lateinit var friendship: Friendship

    @BeforeEach
    fun setup() {
        reset(mockedFriendshipRequestRepository, mockedFriendshipRepository, mockedEventPublisher)
        request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        friendship = request.toFriendship()
    }
}
