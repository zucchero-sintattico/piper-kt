package piperkt.services.multimedia.domain.direct

import piperkt.services.multimedia.common.EntityId
import piperkt.services.multimedia.domain.user.Username

class DirectId(value: Set<Username>) : EntityId<Set<Username>>(value)
