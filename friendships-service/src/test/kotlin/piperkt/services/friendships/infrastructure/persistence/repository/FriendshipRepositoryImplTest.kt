package piperkt.services.friendships.infrastructure.persistence.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.domain.factory.FriendshipFactory
import piperkt.services.friendships.domain.factory.MessageFactory

@MicronautTest
class FriendshipRepositoryImplTest(private val friendshipRepository: FriendshipRepository) :
    AnnotationSpec() {

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
            message = MessageFactory.createMessage(sender = "alice", content = "Hello, Bob!")
        )
        friendshipRepository.update(friendship)
        friendshipRepository.findByMembers("alice", "bob")?.let {
            it.messages.size shouldBe 1
            it.messages[0].content shouldBe "Hello, Bob!"
        }
    }
}
