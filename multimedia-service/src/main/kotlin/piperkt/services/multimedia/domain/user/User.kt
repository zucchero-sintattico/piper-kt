package piperkt.services.multimedia.domain.user

import piperkt.services.multimedia.domain.AggregateRoot

class User(id: UserId, val username: String = id.value) : AggregateRoot<UserId>(id)
