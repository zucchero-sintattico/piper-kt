package piperkt.services.multimedia.domain.users

data class UserId(val value: String)

fun String.toUserId() = UserId(this)
