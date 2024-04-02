package piperkt.services.multimedia.domain.direct

import piperkt.services.multimedia.domain.EntityId
import piperkt.services.multimedia.domain.user.UserId

class DirectId(value: Set<UserId>) : EntityId<Set<UserId>>(value)
