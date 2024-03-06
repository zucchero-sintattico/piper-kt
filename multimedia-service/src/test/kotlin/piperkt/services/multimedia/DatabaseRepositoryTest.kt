package piperkt.services.multimedia

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.UserRepository

@MicronautTest
class DatabaseRepositoryTest(private val userRepository: UserRepository) : AnnotationSpec() {

    @Test
    fun testFindByEmail() {
        val user = User("email", "password")
        userRepository.save(user)
        val foundUser = userRepository.findByEmail("email")
        foundUser shouldBe user
    }
}
