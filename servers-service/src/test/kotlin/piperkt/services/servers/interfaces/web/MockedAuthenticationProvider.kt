package piperkt.services.servers.interfaces.web

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
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
        println("MockedAuthenticationProvider.authenticate")
        return AuthenticationResponse.success(authRequest.identity)
    }
}