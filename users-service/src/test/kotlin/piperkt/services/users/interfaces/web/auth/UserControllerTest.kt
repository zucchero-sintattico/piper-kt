package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client

@Client("/")
interface UserControllerClient {
    @Get("whoami") fun whoami(@Header(HttpHeaders.AUTHORIZATION) authorization: String)
}

class UserControllerTest(
    private val userControllerClient: UserControllerClient,
) : IntegrationTest.FunSpec({})
