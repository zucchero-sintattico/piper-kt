package piperkt.services.servers.interfaces.web.api.errors

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorResponse(
    val message: String,
)
