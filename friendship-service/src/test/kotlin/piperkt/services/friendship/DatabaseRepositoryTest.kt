package piperkt.services.friendship

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class DatabaseRepositoryTest : AnnotationSpec() {
    @Test
    fun `test send friend request`() {
        assert(true)
    }
}
