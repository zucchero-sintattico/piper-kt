package piperkt.services.multimedia.application

import piperkt.services.multimedia.application.api.query.GetChannelSessionQuery
import piperkt.services.multimedia.application.api.query.GetDirectSessionQuery
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery

interface MultimediaApi {

    fun getUsersInSession(
        query: GetUserInSessionQuery
    ): Result<GetUserInSessionQuery.Response, GetUserInSessionQuery.Errors>

    fun getDirectSession(
        query: GetDirectSessionQuery
    ): Result<GetDirectSessionQuery.Response, GetDirectSessionQuery.Error>

    fun getChannelSession(
        query: GetChannelSessionQuery
    ): Result<GetChannelSessionQuery.Response, GetChannelSessionQuery.Error>
}
