package piperkt.services.multimedia.interfaces.websockets

enum class MultimediaProtocolEvent(val event: String) {
    // Received
    JOIN("join"),
    OFFER("offer"),
    ANSWER("answer"),
    ICE_CANDIDATE("candidate"),

    // Sent
    NOT_AUTHENTICATED("notAuthenticated"),
    USER_JOIN("user-join"),
    USER_LEFT("user-left"),
    OFFER_RECEIVED("offer-received"),
    ANSWER_RECEIVED("answer-received"),
    ICE_CANDIDATE_RECEIVED("candidate-received"),
}
