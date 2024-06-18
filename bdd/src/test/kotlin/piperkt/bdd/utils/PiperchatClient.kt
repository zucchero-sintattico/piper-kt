package piperkt.bdd.utils

import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import java.util.*
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.users.interfaces.web.api.RegisterApi
import piperkt.services.users.presentation.user.UserDTO

open class PiperchatClient : AbstractHttpClient() {
    private fun randomUsername() = UUID.randomUUID().toString()

    private var user: UserDTO? = null
    private var userToken: BearerAccessRefreshToken? = null

    fun register() {
        user =
            POST(
                "/auth/register",
                RegisterApi.RegisterRequest(randomUsername(), "password", "email", "description")
            )
    }

    fun login() {
        userToken = POST("/auth/login", UsernamePasswordCredentials(user!!.username, "password"))
    }

    fun registerAndLogin() {
        register()
        login()
    }

    fun getUsername(): String = user!!.username

    fun isLoggedIn(): Boolean = userToken != null

    fun logout() {
        userToken = null
    }

    fun isRegistered(): Boolean = user != null

    fun unregister() {
        user = null
    }

    private var createdServerId: String? = null

    fun getCreatedServerId(): String = createdServerId!!

    fun createServer(serverName: String, serverDescription: String) {
        createdServerId =
            POST("/servers", ServerApi.CreateServerApi.Request(serverName, serverDescription))
    }

    fun myServers(): ServerApi.GetServersFromUserApi.Response {
        return GET("/servers")
    }

    fun sendFriendRequest(receiver: String) {
        POST<Unit>("/friends/request/send", FriendshipApi.SendFriendshipRequest.Request(receiver))
    }

    fun acceptFriendRequest(sender: String) {
        POST<Unit>("/friends/request/accept", FriendshipApi.AcceptFriendshipRequest.Request(sender))
    }

    fun rejectFriendRequest(sender: String) {
        POST<Unit>(
            "/friends/request/reject",
            FriendshipApi.DeclineFriendshipRequest.Request(sender)
        )
    }

    fun getFriendRequests(): FriendshipApi.GetFriendshipRequests.Response {
        return GET("/friends/request")
    }

    fun getFriends(): FriendshipApi.GetFriendships.Response {
        return GET("/friends")
    }

    fun sendMessageToFriend(friend: String, message: String) {
        POST<Unit>("/users/${friend}/messages", FriendshipApi.SendMessage.Request(message))
    }

    fun getMessagesFromFriend(friend: String): FriendshipApi.GetFriendshipMessages.Response {
        return GET("/users/${friend}/messages")
    }
}
