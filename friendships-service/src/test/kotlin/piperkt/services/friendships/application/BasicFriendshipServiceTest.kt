package piperkt.services.friendships.application

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset

open class BasicFriendshipServiceTest : AnnotationSpec() {

    protected val mockedRepository: FriendshipAggregateRepository = mock()
    protected val service = FriendshipService(mockedRepository)

    @BeforeEach
    fun setup() {
        reset(mockedRepository)
    }
}
