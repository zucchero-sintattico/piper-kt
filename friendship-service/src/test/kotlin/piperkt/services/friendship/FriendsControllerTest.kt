package piperkt.services.friendship

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@Client("/friend")
interface ServerClient {
    @Get("/") fun getFriend(): String
}

@MicronautTest
class FriendsControllerTest(private val client: ServerClient) : AnnotationSpec() {

    // return Unauthorized
    @Test
    fun `get friends without authentication`() {
        val response = client.getFriend()
        response shouldBe "Unauthorized"
    }
}
