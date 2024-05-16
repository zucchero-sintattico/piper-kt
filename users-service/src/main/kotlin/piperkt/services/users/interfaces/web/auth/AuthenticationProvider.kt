package piperkt.services.users.interfaces.web.auth

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton
import piperkt.services.users.application.AuthService
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserError.UserNotFound

/**
 * The authentication provider for the users service. It provides the authentication logic for the
 * users service. It responds to /auth/login requests.
 *
 * @param authService The authentication service.
 */
@Singleton
class AuthenticationProvider<B>(private val authService: AuthService) :
    HttpRequestAuthenticationProvider<B> {

    override fun authenticate(
        requestContext: HttpRequest<B>?,
        authRequest: AuthenticationRequest<String, String>,
    ): AuthenticationResponse {
        val username = authRequest.identity
        val password = authRequest.secret
        return try {
            authService.login(username, password)
            AuthenticationResponse.success(username)
        } catch (_: UserNotFound) {
            AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
        } catch (_: UserError.InvalidPassword) {
            AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
        }
    }
}
