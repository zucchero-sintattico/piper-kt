package piperkt.services.multimedia.domain.user

import piperkt.common.AggregateRoot

class User(id: Username, val username: String = id.value) : AggregateRoot<Username>(id)
