package piperkt.services.friendship

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@Client("/friend")
interface ServerClient {
    @Get("/") fun getFriend(): String

    @Get("/request") fun getFriendRequest(): String
}

@MicronautTest
class FriendsControllerTest(private val client: ServerClient) : AnnotationSpec() {

    @Test
    fun testGetEmptyFriends() {
        val response = client.getFriend()
        assert(response == "[]")
    }

    @Test
    fun testGetEmptyFriendRequests() {
        val response = client.getFriendRequest()
        assert(response == "[]")
    }
}
