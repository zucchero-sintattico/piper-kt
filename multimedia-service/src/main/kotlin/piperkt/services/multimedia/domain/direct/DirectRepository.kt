package piperkt.services.multimedia.domain.direct

import piperkt.common.ddd.Repository
import piperkt.services.multimedia.domain.user.Username

interface DirectRepository : Repository<DirectId, Direct> {
    fun findByUsers(users: Set<Username>): Direct?
}
