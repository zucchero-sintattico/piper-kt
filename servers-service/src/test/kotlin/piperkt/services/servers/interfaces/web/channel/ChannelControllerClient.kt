package piperkt.services.servers.interfaces.web.channel

import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
import piperkt.services.servers.interfaces.web.authOf

@Client("/servers")
@Retryable
interface ChannelControllerClient {

    @Post("/{serverId}/channels/")
    fun createChannel(
        @PathVariable serverId: String,
        @Body request: ChannelApi.CreateChannelApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.CreateChannelApi.Response

    @Put("/{serverId}/channels/{channelId}")
    fun updateChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.UpdateChannelApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.UpdateChannelApi.Response

    @Delete("/{serverId}/channels/{channelId}")
    fun deleteChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.DeleteChannelApi.Response

    @Post("/{serverId}/channels/")
    fun getChannelsFromServer(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.GetChannelsFromServerApi.Response

    @Get("/{serverId}/channels/{channelId}/messages")
    fun getChannelMessages(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @QueryValue from: Int,
        @QueryValue to: Int,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.GetChannelMessagesApi.Response


    @Post("/{serverId}/channels/{channelId}/messages")
    fun sendMessageToChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.SendMessageToChannelApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): ChannelApi.SendMessageToChannelApi.Response


}