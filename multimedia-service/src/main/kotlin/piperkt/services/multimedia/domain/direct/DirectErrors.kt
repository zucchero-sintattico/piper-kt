package piperkt.services.multimedia.domain.direct

sealed class DirectErrors : Exception() {
    data class DirectNotFound(val id: DirectId) : DirectErrors()
}
