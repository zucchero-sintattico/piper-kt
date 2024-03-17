package piperkt.services.friendship

import io.micronaut.http.client.HttpClient

class FriendsControllerTest {

    // @Inject
    private lateinit var client: HttpClient

    // @Test getFriends, force 3 friend in the database and assert the response
    fun testGetEmptyFriends() {
        val response = client.toBlocking().retrieve("/friend")
        assert(response == "[]")
    }
}
