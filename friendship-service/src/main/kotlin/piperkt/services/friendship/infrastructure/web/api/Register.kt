package piperkt.services.friendship.infrastructure.web.api

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class RegisterRequest(
    @NotBlank val email: String,
    @NotBlank val password: String,
)

@Serdeable
data class RegisterResponse(
    @NotBlank val email: String,
)
