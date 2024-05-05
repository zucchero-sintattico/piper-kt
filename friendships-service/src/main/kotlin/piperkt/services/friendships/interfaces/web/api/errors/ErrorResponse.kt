package piperkt.services.friendships.interfaces.web.api.errors

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorResponse(
    val message: String,
)
