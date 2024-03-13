package piperkt.services.multimedia.domain.users

import piperkt.services.multimedia.domain.Id

typealias UserId = Id<String>

fun String.toUserId() = UserId(this)
