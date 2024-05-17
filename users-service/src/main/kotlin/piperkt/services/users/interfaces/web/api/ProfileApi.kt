package piperkt.services.users.interfaces.web.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.presentation.user.UserDTO

@Secured(SecurityRule.IS_AUTHENTICATED)
interface ProfileApi {

    @Serdeable data class UpdateDescriptionRequest(val description: String?)

    @Put("/profile/description")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "The user description was updated."),
        ApiResponse(responseCode = "404", description = "The user was not found."),
    )
    fun updateDescription(principal: Principal, @Body request: UpdateDescriptionRequest): UserDTO

    @Serdeable data class UpdateProfilePictureRequest(val profilePicture: String?)

    @Put("/profile/photo")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "The user profile picture was updated."),
        ApiResponse(responseCode = "404", description = "The user was not found."),
    )
    fun updateProfilePicture(
        principal: Principal,
        @Body request: UpdateProfilePictureRequest
    ): UserDTO

    sealed interface Errors {
        @Serdeable data class UserNotFound(val username: String) : Errors
    }

    @Error(UserError.UserNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onUserNotFound(error: UserError.UserNotFound): Errors.UserNotFound {
        return Errors.UserNotFound(error.username.value)
    }
}
