package piperkt.services.servers.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface ChannelHttpControllerApi {

    fun createChannel(
        @PathVariable serverId: String,
        @Body request: ChannelApi.CreateChannelApi.Request,
        principal: Principal
    ): ChannelApi.CreateChannelApi.Response

    fun updateChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.UpdateChannelApi.Request,
        principal: Principal
    ): ChannelApi.UpdateChannelApi.Response

    fun deleteChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        principal: Principal
    ): ChannelApi.DeleteChannelApi.Response

    fun getChannelsFromServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ChannelApi.GetChannelsFromServerApi.Response

    fun getChannelMessages(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @QueryValue from: Int,
        @QueryValue to: Int,
        principal: Principal
    ): ChannelApi.GetChannelMessagesApi.Response

    fun sendMessageToChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.SendMessageToChannelApi.Request,
        principal: Principal
    ): ChannelApi.SendMessageToChannelApi.Response
}
