package piperkt.services.multimedia.interfaces.websockets

import io.micronaut.serde.annotation.Serdeable

sealed interface MultimediaProtocolMessage {

    @Serdeable data class JoinMessage(val sessionId: String) : MultimediaProtocolMessage

    @Serdeable
    data class OfferMessage(val from: String, val to: String, val offer: String) :
        MultimediaProtocolMessage

    @Serdeable
    data class AnswerMessage(val from: String, val to: String, val answer: String) :
        MultimediaProtocolMessage

    @Serdeable
    data class IceCandidateMessage(val from: String, val to: String, val candidate: String) :
        MultimediaProtocolMessage

    @Serdeable
    data class UserJoined(val sessionId: String, val userId: String) : MultimediaProtocolMessage
}
