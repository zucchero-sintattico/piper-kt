package piperkt.services.multimedia.domain.direct

import piperkt.services.multimedia.domain.user.Username

sealed class DirectErrors : Exception() {
    data class DirectNotFound(val users: Set<Username>) : DirectErrors()
}
