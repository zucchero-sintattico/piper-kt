package piperkt.services.friendships.application

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

open class BasicFriendshipServiceTest : AnnotationSpec() {

    protected val mockedRepository: FriendshipAggregateRepository = mock()
    protected val service = FriendshipService(mockedRepository)
    lateinit var request: FriendshipRequest

    @BeforeEach
    fun setup() {
        reset(mockedRepository)
        request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
    }
}
