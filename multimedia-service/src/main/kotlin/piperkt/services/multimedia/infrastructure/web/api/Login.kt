package piperkt.services.multimedia.infrastructure.web.api

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class LoginRequest(
    @NotBlank val email: String,
    val password: String,
)

@Serdeable data class LoginResponse(val email: String)
