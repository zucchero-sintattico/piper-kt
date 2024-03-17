package piperkt.services.friendship.infrastructure.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import piperkt.services.friendship.application.FriendService
import piperkt.services.friendship.infrastructure.web.api.FriendsResponse

@Controller("/friend")
class FriendsHTTPController(private val friendService: FriendService) {

    private var testUserName = "user"

    @Get("/friends")
    fun getFriends(): List<String> {
        val result = friendService.getFriends(testUserName)
        return FriendsResponse(result.map { it.username }).friends
    }
}
