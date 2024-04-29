package piperkt.services.friendships.interfaces.web

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

@Client("/")
@Retryable
interface FriendshipHttpClient {

    @Post("friends/requests/send/")
    fun sendFriendshipRequest(
        @Body request: FriendshipApi.SendFriendshipRequest.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.SendFriendshipRequest.Response>

    @Post("friends/requests/accept/")
    fun acceptFriendshipRequest(
        @Body request: FriendshipApi.AcceptFriendshipRequest.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.AcceptFriendshipRequest.Response>

    @Post("friends/requests/decline/")
    fun declineFriendshipRequest(
        @Body request: FriendshipApi.DeclineFriendshipRequest.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.DeclineFriendshipRequest.Response>

    @Get("friends/requests/")
    fun getFriendshipRequests(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.GetFriendshipRequests.Response>

    @Get("friends/")
    fun getFriendships(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String
    ): HttpResponse<FriendshipApi.GetFriendships.Response>


    @Get("users/{friendUsername}/messages")
    fun getMessages(
        @PathVariable friendUsername: String,
        @QueryValue from: Int,
        @QueryValue limit: Int,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.GetFriendshipMessages.Response>

    @Post("users/{friendUsername}/messages")
    fun sendMessage(
        @PathVariable friendUsername: String,
        @Body content: FriendshipApi.SendMessage.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<FriendshipApi.SendMessage.Response>
}
