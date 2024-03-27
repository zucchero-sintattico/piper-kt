package piperkt.services.multimedia.interfaces.websockets.api

import kotlinx.serialization.Serializable
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage.*

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

interface MultimediaServerApi {

    fun onConnect()

    fun onDisconnect()

    fun onUserJoin(message: JoinMessage)

    fun onOffer(message: OfferMessage)

    fun onAnswer(message: AnswerMessage)

    fun onIceCandidate(message: IceCandidateMessage)
}
