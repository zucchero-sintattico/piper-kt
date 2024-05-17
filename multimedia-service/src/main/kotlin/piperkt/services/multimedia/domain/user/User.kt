package piperkt.services.multimedia.domain.user

import piperkt.common.ddd.AggregateRoot
import piperkt.common.ddd.EntityId

class Username(value: String) : EntityId<String>(value)

class User(id: Username, val username: String = id.value) : AggregateRoot<Username>(id)
