package piperkt.services.friendships.interfaces.web

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.assertThrows
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

class FriendshipHttpControllerTest(
    private var client: FriendshipHttpClient,
    private var requestRepository: FriendshipRequestRepository,
    private var friendshipRepository: FriendshipRepository
) : IntegrationTest() {

    @BeforeEach
    fun setup() {
        requestRepository.deleteAll()
        friendshipRepository.deleteAll()
    }

    @Test
    fun `should return a 200 status code when sending a friend request`() {
        val response =
            client.sendFriendshipRequest(
                request = FriendshipApi.SendFriendshipRequest.Request(receiver = "receiver")
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 409 status code when sending a friend request to the same user twice`() {
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
    fun `should return a 409 status code when sending a friend request to a friend`() {
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
    fun `should return a 200 status code when get all friends request`() {
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
    fun `should return a 200 status code when accepting a friend request`() {
        client.sendFriendshipRequest(
            request =
                FriendshipApi.SendFriendshipRequest.Request(receiver = "whoReceivedTheRequest"),
            authorization = authOf("whoSentTheRequest")
        )

        client
            .acceptFriendshipRequest(
                request =
                    FriendshipApi.AcceptFriendshipRequest.Request(sender = "whoSentTheRequest"),
                authorization = authOf("whoReceivedTheRequest")
            )
            .let { it.status() shouldBe HttpStatus.OK }
    }

    @Test
    fun `should return a 404 status code when accepting a friend request that does not exist`() {
        client
            .acceptFriendshipRequest(
                request = FriendshipApi.AcceptFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiverWithNoRequests")
            )
            .let { it.status() shouldBe HttpStatus.NOT_FOUND }
    }

    @Test
    fun `should return a 200 status code when declining a friend request`() {
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
    fun `should return a 404 status code when declining a friend request that does not exist`() {
        client
            .declineFriendshipRequest(
                request = FriendshipApi.DeclineFriendshipRequest.Request(sender = "user"),
                authorization = authOf("receiverWithNoRequests")
            )
            .let { it.status() shouldBe HttpStatus.NOT_FOUND }
    }

    @Test
    fun `should return a 200 status code when getting all friends`() {
        client.getFriendships(authorization = authOf("user")).status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 200 status code when getting all messages from a friendship`() {
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
    fun `should return a 404 status code when getting all messages from a friendship that does not exist`() {
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
    fun `should return a 200 status code when sending a message to a friend`() {
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

    @Test
    fun `should return a 404 status code when sending a message to a friend that does not exist`() {
        client
            .sendMessage(
                friendUsername = "receiver",
                content = FriendshipApi.SendMessage.Request(content = "Hello")
            )
            .status() shouldBe HttpStatus.NOT_FOUND
    }
}
