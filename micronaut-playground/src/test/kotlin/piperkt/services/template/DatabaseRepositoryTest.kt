package piperkt.services.template

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.template.domain.UserRepository

@MicronautTest
class DatabaseRepositoryTest(private val userRepository: UserRepository) : AnnotationSpec() {
  @Test
  fun testUserRepository() {
    val users = userRepository.findByEmail("email")
    assert(users == null)
  }
}
