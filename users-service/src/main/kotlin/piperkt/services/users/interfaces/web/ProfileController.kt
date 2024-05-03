package piperkt.services.users.interfaces.web

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import java.security.Principal
import piperkt.services.users.application.UserService
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for updating the user profile.
 *
 * @param userService The service to handle the profile updates.
 */
@Controller("/profile")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ProfileController(private val userService: UserService) {

    @Serdeable data class UpdateDescriptionRequest(val description: String?)

    /**
     * Update the description of the user.
     *
     * @param principal The current user.
     * @param request The request to update the description.
     */
    @Put("/description")
    fun updateDescription(principal: Principal, @Body request: UpdateDescriptionRequest): UserDTO =
        userService.updateUserDescription(principal.name, request.description).toDTO()

    @Serdeable data class UpdateProfilePictureRequest(val profilePicture: String?)

    /**
     * Update the profile picture of the user.
     *
     * @param principal The current user.
     * @param request The request to update the profile picture.
     */
    @Put("/photo")
    fun updateProfilePicture(
        principal: Principal,
        @Body request: UpdateProfilePictureRequest
    ): UserDTO =
        userService.updateUserProfilePicture(principal.name, request.profilePicture).toDTO()
}
