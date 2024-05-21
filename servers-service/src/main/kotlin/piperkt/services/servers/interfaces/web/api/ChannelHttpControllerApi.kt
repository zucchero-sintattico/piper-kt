package piperkt.services.servers.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface ChannelHttpControllerApi {

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Channel created successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    fun createChannel(
        @PathVariable serverId: String,
        @Body request: ChannelApi.CreateChannelApi.Request,
        principal: Principal
    ): ChannelApi.CreateChannelApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Channel updated successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Channel not found"),
    )
    fun updateChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.UpdateChannelApi.Request,
        principal: Principal
    ): ChannelApi.UpdateChannelApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Channel deleted successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Channel not found"),
    )
    fun deleteChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        principal: Principal
    ): ChannelApi.DeleteChannelApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Channels found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    fun getChannelsFromServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ChannelApi.GetChannelsFromServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Messages found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Channel not found"),
    )
    fun getChannelMessages(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @QueryValue from: Int,
        @QueryValue limit: Int,
        principal: Principal
    ): ChannelApi.GetChannelMessagesApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Message sent successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Channel not found"),
    )
    fun sendMessageToChannel(
        @PathVariable serverId: String,
        @PathVariable channelId: String,
        @Body request: ChannelApi.SendMessageToChannelApi.Request,
        principal: Principal
    ): ChannelApi.SendMessageToChannelApi.Response
}
