package piperkt.services.users.interfaces.web.auth

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton

@Singleton
@Requires(env = ["test"])
@Requires(property = "mocked-authentication", notEquals = "false")
class MockedAuthenticationProvider : HttpRequestAuthenticationProvider<String> {
    override fun authenticate(
        requestContext: HttpRequest<String>?,
        authRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        return AuthenticationResponse.success(authRequest.identity)
    }
}

@Property(name = "mocked-authentication", value = "false") annotation class NoMockedAuthentication
