package piperkt.services.multimedia.domain.directs

import piperkt.services.multimedia.domain.sessions.Session

interface DirectRepository {
    fun getDirectById(directId: DirectId): Direct?

    fun getSessionInDirect(directId: DirectId): Session
}
