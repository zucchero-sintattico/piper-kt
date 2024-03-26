package piperkt.services.multimedia.interfaces.websockets.api

interface MultimediaServerApi {

    fun onUserJoin(username: String, sessionId: String)

    fun onUserLeave(username: String, sessionId: String)

    fun onOffer(username: String, sessionId: String, offer: String)

    fun onAnswer(username: String, sessionId: String, answer: String)

    fun onIceCandidate(username: String, sessionId: String, candidate: String)
}
