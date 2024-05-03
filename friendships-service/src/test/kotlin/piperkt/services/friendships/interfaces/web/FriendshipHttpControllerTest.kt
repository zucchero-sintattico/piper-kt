package piperkt.services.friendships.interfaces.web

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

class FriendshipHttpControllerTest : IntegrationTest() {

    @Inject lateinit var client: FriendshipHttpClient

    @Inject lateinit var requestRepository: FriendshipRequestRepository

    @Inject lateinit var friendshipRepository: FriendshipRepository

    @BeforeEach
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
    fun `should not send a friend request if the request already exists`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        assertThrows<HttpClientResponseException> {
                client.sendFriendshipRequest(
                    request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
                )
            }
            .let { it.status shouldBe HttpStatus.CONFLICT }
    }

    @Test
    fun `should not send a friend request if they are already friends`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        client.acceptFriendshipRequest(
            request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
            authorization = authOf("receiver")
        )
        assertThrows<HttpClientResponseException> {
                client.sendFriendshipRequest(
                    request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
                )
            }
            .let { it.status shouldBe HttpStatus.CONFLICT }
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
        client.getFriendships(authorization = authOf("user")).status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should get messages`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        client.acceptFriendshipRequest(
            request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
            authorization = authOf("receiver")
        )
        client
            .getMessages(
                friendUsername = "receiver",
                authorization = authOf("user"),
                from = 0,
                limit = 10
            )
            .status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not get messages if they are not friends`() {
        client
            .getMessages(
                friendUsername = "receiver",
                authorization = authOf("user"),
                from = 0,
                limit = 10
            )
            .status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should send a message`() {
        client.sendFriendshipRequest(
            request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
        )
        client.acceptFriendshipRequest(
            request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
            authorization = authOf("receiver")
        )
        client
            .sendMessage(
                friendUsername = "receiver",
                content = FriendshipApi.SendMessage.Request(content = "Hello")
            )
            .status() shouldBe HttpStatus.OK
    }

    //    @Test
    //    fun `should not send a message if they are not friends`() {
    //        client.sendMessage(
    //            friendUsername = "receiver",
    //            content = FriendshipApi.SendMessage.Request(content = "Hello")
    //        ).status() shouldBe HttpStatus.NOT_FOUND
    //    }

}
