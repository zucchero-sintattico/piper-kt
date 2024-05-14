package piperkt.services.multimedia.domain.user

import piperkt.common.AggregateRoot
import piperkt.common.EntityId

class Username(value: String) : EntityId<String>(value)

class User(id: Username, val username: String = id.value) : AggregateRoot<Username>(id)
