package piperkt.services.friendships.interfaces.web

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import jakarta.inject.Inject
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

class FriendshipHttpControllerTest : IntegrationTest() {

    @Inject lateinit var client: FriendshipHttpClient

    @Inject lateinit var requestRepository: FriendshipRequestRepository

    @Inject lateinit var friendshipRepository: FriendshipRepository

    @AfterEach
    fun setup() {
        requestRepository.deleteAll()
        friendshipRepository.deleteAll()
    }

    @Test
    fun `should send a friend request`() {
        val response =
            client.sendFriendshipRequest(
                request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should get friend requests`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        client.getFriendshipRequests(authorization = authOf("receiver")).let {
            it.status() shouldBe HttpStatus.OK
            it.body() shouldBe
                FriendshipApi.GetFriendshipRequests.Response(requests = listOf("user"))
        }
    }

    @Test
    fun `should accept a friend request`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        client
            .acceptFriendshipRequest(
                request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiver")
            )
            .let { it.status() shouldBe HttpStatus.OK }
    }

    @Test
    fun `should not accept a friend request that does not exist`() {
        client
            .acceptFriendshipRequest(
                request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiverWithNoRequests")
            )
            .let { it.status() shouldBe HttpStatus.NOT_FOUND }
    }

    @Test
    fun `should decline a friend request`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver"),
            authorization = authOf("user")
        )
        client
            .declineFriendshipRequest(
                request = FriendshipApi.DeclineFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiver")
            )
            .let {
                println(it.status().reason)
                it.status() shouldBe HttpStatus.OK
            }
    }

    @Test
    fun `should not decline a friend request that does not exist`() {
        client
            .declineFriendshipRequest(
                request = FriendshipApi.DeclineFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiverWithNoRequests")
            )
            .let { it.status() shouldBe HttpStatus.NOT_FOUND }
    }

    @Test
    fun `should get friends`() {
        client.getFriendships(authorization = authOf("user")).let {
            it.status() shouldBe HttpStatus.OK
        }
    }
}
