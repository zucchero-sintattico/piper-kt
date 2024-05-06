package piperkt.services.friendships.infrastructure.persistence.repository

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

class FriendshipRequestRepositoryImplTest(
    private val friendshipRequestRepository: FriendshipRequestRepository
) : IntegrationTest() {

    @Test
    fun `should create a new friendship request`() {
        val friendshipRequest = FriendshipRequestFactory.createFriendshipRequest("alice", "bob")
        friendshipRequestRepository.save(friendshipRequest)
        val entity = friendshipRequestRepository.findByMembers("alice", "bob")
        entity shouldBe friendshipRequest
    }

    @Test
    fun `should delete a friendship request`() {
        val friendshipRequest = FriendshipRequestFactory.createFriendshipRequest("alice", "bob")
        friendshipRequestRepository.save(friendshipRequest)
        friendshipRequestRepository.deleteById(friendshipRequest.id)
        friendshipRequestRepository.findByMembers("alice", "bob") shouldBe null
    }

    @Test
    fun `should find friendship requests from a user`() {
        val friendshipRequest = FriendshipRequestFactory.createFriendshipRequest("alice", "bob")
        friendshipRequestRepository.save(friendshipRequest)
        friendshipRequestRepository.findByReceiver("alice").size shouldBe 0
        friendshipRequestRepository.findByReceiver("bob").size shouldBe 1
    }

    @Test
    fun `should find a friendship request by id`() {
        val friendshipRequest = FriendshipRequestFactory.createFriendshipRequest("alice", "bob")
        friendshipRequestRepository.save(friendshipRequest)
        friendshipRequestRepository.findById(friendshipRequest.id) shouldBe friendshipRequest
    }

    @Test
    fun `should find friendship request from members`() {
        val friendshipRequest = FriendshipRequestFactory.createFriendshipRequest("alice", "bob")
        friendshipRequestRepository.save(friendshipRequest)
        friendshipRequestRepository.findByMembers("alice", "bob") shouldBe friendshipRequest
    }
}
