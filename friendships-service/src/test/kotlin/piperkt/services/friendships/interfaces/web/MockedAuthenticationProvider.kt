package piperkt.services.friendships.interfaces.web

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton

// Enable only in test
@Singleton
@Requires(env = [Environment.TEST])
class MockedAuthenticationProvider : HttpRequestAuthenticationProvider<String> {
    override fun authenticate(
        requestContext: HttpRequest<String>?,
        authRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        return AuthenticationResponse.success(authRequest.identity)
    }
}

fun authOf(username: String): String {
    val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
    return req.headers.get(HttpHeaders.AUTHORIZATION)!!
}
