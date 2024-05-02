package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import piperkt.services.users.application.AuthService
import piperkt.services.users.interfaces.web.ProfileController
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

@Client("/")
interface ProfileControllerClient {

    @Put("profile/description")
    fun updateDescription(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @Body request: ProfileController.UpdateDescriptionRequest
    ): UserDTO

    @Put("profile/photo")
    fun updateProfilePicture(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @Body request: ProfileController.UpdateProfilePictureRequest
    ): UserDTO
}

class ProfileControllerTest(
    private val profileControllerClient: ProfileControllerClient,
    private val authService: AuthService
) :
    IntegrationTest.FunSpec({
        val user = authService.register("user", "password")

        test("updateDescription") {
            val response =
                profileControllerClient.updateDescription(
                    TestUtils.authOf(user.username.value),
                    ProfileController.UpdateDescriptionRequest("description")
                )
            response shouldBe user.copy(description = "description").toDTO()
        }

        test("updateProfilePicture") {
            val response =
                profileControllerClient.updateProfilePicture(
                    TestUtils.authOf(user.username.value),
                    ProfileController.UpdateProfilePictureRequest(byteArrayOf(1, 2, 3))
                )
            response shouldBe user.copy(profilePicture = byteArrayOf(1, 2, 3)).toDTO()
        }
    })
