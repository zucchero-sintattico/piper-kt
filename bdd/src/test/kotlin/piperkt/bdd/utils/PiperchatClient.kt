package piperkt.bdd.utils

import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import io.micronaut.serde.annotation.Serdeable
import java.util.*
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi
import piperkt.services.servers.domain.ChannelType
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
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

    fun createServer() {
        createdServerId =
            POST<ServerApi.CreateServerApi.Response>(
                    "/servers",
                    ServerApi.CreateServerApi.Request(randomUsername(), randomUsername()),
                    userToken
                )
                .serverId
    }

    fun joinServer(serverId: String) {
        POST<ServerApi.AddUserToServerApi.Response>(
            "/servers/${serverId}/users",
            Empty(),
            userToken
        )
    }

    fun myServers(): ServerApi.GetServersFromUserApi.Response {
        return GET("/servers", userToken)
    }

    fun deleteChannel(serverId: String, channelId: String): ChannelApi.DeleteChannelApi.Response {
        return DELETE("/servers/${serverId}/channels/${channelId}", userToken)
    }

    private var createdChannelId: String? = null

    fun getCreatedChannelId(): String = createdChannelId!!

    fun createChannel() {
        createdChannelId =
            POST<ChannelApi.CreateChannelApi.Response>(
                    "/servers/${createdServerId}/channels",
                    ChannelApi.CreateChannelApi.Request(
                        randomUsername(),
                        randomUsername(),
                        ChannelType.TEXT.toString()
                    ),
                    userToken
                )
                .channelId
    }

    fun sendMessageInChannel(serverId: String, channelId: String, message: String) {
        POST<ChannelApi.SendMessageToChannelApi.Response>(
            "/servers/${serverId}/channels/${channelId}/messages",
            ChannelApi.SendMessageToChannelApi.Request(message),
            userToken
        )
    }

    @Serdeable class Empty

    fun sendFriendRequest(receiver: String) {
        POST<Empty>(
            "/friends/requests/send",
            FriendshipApi.SendFriendshipRequest.Request(receiver),
            userToken
        )
    }

    fun acceptFriendRequest(sender: String) {
        POST<Empty>(
            "/friends/requests/accept",
            FriendshipApi.AcceptFriendshipRequest.Request(sender),
            userToken
        )
    }

    fun rejectFriendRequest(sender: String) {
        POST<Empty>(
            "/friends/requests/decline",
            FriendshipApi.DeclineFriendshipRequest.Request(sender),
            userToken
        )
    }

    fun getFriendRequests(): FriendshipApi.GetFriendshipRequests.Response {
        return GET("/friends/requests", userToken)
    }

    fun getFriends(): FriendshipApi.GetFriendships.Response {
        return GET("/friends", userToken)
    }

    fun sendMessageToFriend(friend: String, message: String) {
        POST<Empty>(
            "/users/${friend}/messages",
            FriendshipApi.SendMessage.Request(message),
            userToken
        )
    }

    fun getMessagesFromFriend(friend: String): FriendshipApi.GetFriendshipMessages.Response {
        return GET("/users/${friend}/messages?from=0&limit=10", userToken)
    }
}
