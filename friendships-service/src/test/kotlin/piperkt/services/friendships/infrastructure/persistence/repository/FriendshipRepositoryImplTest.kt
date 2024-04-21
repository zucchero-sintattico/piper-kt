package piperkt.services.friendships.infrastructure.persistence.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.domain.factory.FriendshipFactory

@MicronautTest
class FriendshipRepositoryImplTest(
    private val friendshipRepository: FriendshipRepository
) : AnnotationSpec() {

    @Test
    fun `should create a new friendship`() {
        val friendship = FriendshipFactory.createFriendship("alice", "bob")
        friendshipRepository.save(friendship)
        val entity = friendshipRepository.findByFriendship("alice", "bob")
        entity shouldBe friendship
    }
}