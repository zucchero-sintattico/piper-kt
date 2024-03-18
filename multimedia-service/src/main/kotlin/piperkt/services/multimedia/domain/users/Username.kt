package piperkt.services.multimedia.domain.users

data class Username(val value: String)

fun String.toUserId() = Username(this)
