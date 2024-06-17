package piperkt.services.friendships.infrastructure.persistence.repository

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import piperkt.services.friendships.domain.FriendshipRepository
import piperkt.services.friendships.domain.factory.FriendshipFactory
import piperkt.services.friendships.domain.factory.MessageFactory

class FriendshipRepositoryImplTest(private val friendshipRepository: FriendshipRepository) :
    IntegrationTest() {

    @Test
    fun `should create a new friendship`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        val entity = friendshipRepository.findByMembers("alice", "bob")
        entity shouldBe friendship
    }

    @Test
    fun `should delete a friendship`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        friendshipRepository.deleteById(friendship.id)
        friendshipRepository.findByMembers("alice", "bob") shouldBe null
    }

    @Test
    fun `should find friendships from a user`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        friendshipRepository.findByUser("alice").size shouldBe 1
    }

    @Test
    fun `should find a friendship by id`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        friendshipRepository.findById(friendship.id) shouldBe friendship
    }

    @Test
    fun `should find friendship from members`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        friendshipRepository.findByMembers("alice", "bob") shouldBe friendship
    }

    @Test
    fun `should update a friendship after sending a message`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        friendship.addMessage(
            directMessage = MessageFactory.createMessage(sender = "alice", content = "Hello, Bob!")
        )
        friendshipRepository.update(friendship)
        friendshipRepository.findByMembers("alice", "bob")?.let {
            it.directMessages.size shouldBe 1
            it.directMessages[0].content shouldBe "Hello, Bob!"
        }
    }
}
