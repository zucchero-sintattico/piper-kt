package piperkt.services.friendships.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface DirectHttpApi {


}
