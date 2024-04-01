package piperkt.services.friendship.domain

data class Message (
    val sender: String,
    val content: String,
    val timestamp: Long
)

data class Direct (
    val participants: MutableList<String>,
    val messages: MutableList<Message>
)