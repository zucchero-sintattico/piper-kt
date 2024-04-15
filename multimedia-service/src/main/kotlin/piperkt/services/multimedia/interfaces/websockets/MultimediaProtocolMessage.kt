package piperkt.services.multimedia.interfaces.websockets

import kotlinx.serialization.Serializable

sealed interface MultimediaProtocolMessage {

    @Serializable data class JoinMessage(val sessionId: String) : MultimediaProtocolMessage

    @Serializable
    data class OfferMessage(val from: String, val to: String, val offer: String) :
        MultimediaProtocolMessage

    @Serializable
    data class AnswerMessage(val from: String, val to: String, val answer: String) :
        MultimediaProtocolMessage

    @Serializable
    data class IceCandidateMessage(val from: String, val to: String, val candidate: String) :
        MultimediaProtocolMessage
}
