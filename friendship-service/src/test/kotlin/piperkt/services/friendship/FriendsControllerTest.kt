package piperkt.services.friendship

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.security.authentication.Authentication
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*
import piperkt.services.friendship.application.FriendService
import piperkt.services.friendship.infrastructure.persistence.model.FriendsModelRepository
import piperkt.services.friendship.infrastructure.persistence.model.UserEntity
import piperkt.services.friendship.infrastructure.web.FriendsHTTPController

@MicronautTest
class FriendsControllerTest(private val friendsModelRepository: FriendsModelRepository) :
    AnnotationSpec() {

    private val friendService = mockk<FriendService>()
    private val authentication = mockk<Authentication>()
    private val controller = FriendsHTTPController(friendService)

    @BeforeAll
    fun populateDataBase() {
        val user1 = UserEntity("user1", mutableListOf(), mutableListOf(), mutableListOf())
        val user2 = UserEntity("user2", mutableListOf(), mutableListOf(), mutableListOf())
        val user3 = UserEntity("user3", mutableListOf(), mutableListOf(), mutableListOf())
        val user4 = UserEntity("user4", mutableListOf(), mutableListOf(), mutableListOf())
        friendsModelRepository.save(user1)
        friendsModelRepository.save(user2)
        friendsModelRepository.save(user3)
        friendsModelRepository.save(user4)
    }

    @Test
    fun `getFriends returns friends of authenticated user`() {
        every { authentication.name } returns "user1"
        every { friendService.getFriends("user1") } returns listOf("user2", "user3")

        val result = controller.getFriends(authentication)

        result shouldBe listOf("user2", "user3")
    }

    @Test
    fun `getFriendRequests returns friend requests of authenticated user`() {
        every { authentication.name } returns "user1"
        every { friendService.getFriendRequests("user1") } returns listOf("user2", "user3")

        val result = controller.getFriendRequests(authentication)

        result shouldBe listOf("user2", "user3")
    }
}
