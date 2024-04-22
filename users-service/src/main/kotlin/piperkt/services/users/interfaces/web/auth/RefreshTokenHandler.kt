package piperkt.services.users.interfaces.web.auth

import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import piperkt.services.users.application.AuthService
import piperkt.services.users.domain.user.UserError

@Singleton
class RefreshTokenHandler(private val authService: AuthService) : RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        authService.saveRefreshToken(event.authentication.name, event.refreshToken)
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
        return try {
            val user = authService.findUserByRefreshToken(refreshToken)
            Publisher { subscriber ->
                subscriber.onNext(Authentication.build(user.username.value, emptyList()))
                subscriber.onComplete()
            }
        } catch (e: UserError.RefreshTokenNotFound) {
            Publisher { subscriber -> subscriber.onError(e) }
        }
    }
}
