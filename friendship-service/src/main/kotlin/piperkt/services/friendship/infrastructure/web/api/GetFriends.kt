package piperkt.services.friendship.infrastructure.web.api

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable data class FriendsResponse(@NotBlank val friends: List<String>)

@Serdeable data class FriendsRequest(@NotBlank val username: List<String>)
