package piperkt.services.friendships.application

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.Mockito.reset
import org.mockito.kotlin.mock
import piperkt.common.events.FriendshipEventPublisher
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

open class BasicFriendshipServiceTest : AnnotationSpec() {

    protected val mockedRepository: FriendshipAggregateRepository = mock()
    protected val mockedEventPublisher: FriendshipEventPublisher = mock()
    protected val service = FriendshipService(mockedRepository, mockedEventPublisher)
    lateinit var request: FriendshipRequest

    @BeforeEach
    fun setup() {
        reset(mockedRepository, mockedEventPublisher)
        request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
    }
}
