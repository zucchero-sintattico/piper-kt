package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import piperkt.services.users.application.RegisterRequest

@Client
interface RegisterClient {
    @Post("/register") fun register(registerRequest: RegisterRequest)
}

class RegisterControllerTest : IntegrationTest.FunSpec({})
