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

@Controller("/profile")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ProfileController(private val userService: UserService) {

    @Serdeable data class UpdateDescriptionRequest(val description: String)

    @Put("/description")
    fun updateDescription(principal: Principal, @Body request: UpdateDescriptionRequest): UserDTO =
        userService.updateUserDescription(principal.name, request.description).toDTO()

    @Serdeable data class UpdateProfilePictureRequest(val profilePicture: ByteArray)

    @Put("/photo")
    fun updateProfilePicture(
        principal: Principal,
        @Body request: UpdateProfilePictureRequest
    ): UserDTO =
        userService.updateUserProfilePicture(principal.name, request.profilePicture).toDTO()
}
