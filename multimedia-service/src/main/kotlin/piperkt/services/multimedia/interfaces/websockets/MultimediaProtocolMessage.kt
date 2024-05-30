package piperkt.services.multimedia.interfaces.websockets

import io.micronaut.serde.annotation.Serdeable

data class RTCSessionDescription(
    val sdp: String?,
    val type: String?,
)

data class RTCIceCandidate(
    val candidate: String?,
    val sdpMid: String?,
    val sdpMLineIndex: Int?,
    val usernameFragment: String?,
)

sealed interface MultimediaProtocolMessage {

    @Serdeable data class JoinMessage(val sessionId: String) : MultimediaProtocolMessage

    @Serdeable
    data class OfferMessage(val from: String, val to: String, val offer: RTCSessionDescription) :
        MultimediaProtocolMessage

    @Serdeable
    data class AnswerMessage(val from: String, val to: String, val answer: RTCSessionDescription) :
        MultimediaProtocolMessage

    @Serdeable
    data class IceCandidateMessage(
        val from: String,
        val to: String,
        val candidate: RTCIceCandidate
    ) : MultimediaProtocolMessage

    @Serdeable data class UserJoined(val userId: String) : MultimediaProtocolMessage

    @Serdeable data class UserLeft(val userId: String) : MultimediaProtocolMessage
}
