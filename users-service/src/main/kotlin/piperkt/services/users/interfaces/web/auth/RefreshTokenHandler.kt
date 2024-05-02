package piperkt.services.users.interfaces.web.auth

import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import piperkt.services.users.application.AuthService
import piperkt.services.users.domain.user.UserError
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class RefreshTokenHandler(private val authService: AuthService) : RefreshTokenPersistence {

    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        authService.saveRefreshToken(event.authentication.name, event.refreshToken)
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
        return Flux.create(
            { emitter: FluxSink<Authentication> ->
                try {
                    val user = authService.getUserByRefreshToken(refreshToken)
                    emitter.next(Authentication.build(user.username.value))
                    emitter.complete()
                } catch (_: UserError.RefreshTokenNotFound) {
                    emitter.error(
                        OauthErrorResponseException(
                            IssuingAnAccessTokenErrorCode.INVALID_GRANT,
                            "refresh token not found",
                            null
                        )
                    )
                }
            },
            FluxSink.OverflowStrategy.ERROR
        )
    }
}
