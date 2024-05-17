package piperkt.services.users.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.users.application.UserService
import piperkt.services.users.interfaces.web.api.ProfileApi
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for updating the user profile.
 *
 * @param userService The service to handle the profile updates.
 */
@Controller
class ProfileController(private val userService: UserService) : ProfileApi {

    /**
     * Update the description of the user.
     *
     * @param principal The current user.
     * @param request The request to update the description.
     */
    override fun updateDescription(
        principal: Principal,
        request: ProfileApi.UpdateDescriptionRequest
    ): UserDTO = userService.updateUserDescription(principal.name, request.description).toDTO()

    /**
     * Update the profile picture of the user.
     *
     * @param principal The current user.
     * @param request The request to update the profile picture.
     */
    override fun updateProfilePicture(
        principal: Principal,
        request: ProfileApi.UpdateProfilePictureRequest,
    ): UserDTO =
        userService.updateUserProfilePicture(principal.name, request.profilePicture).toDTO()
}
