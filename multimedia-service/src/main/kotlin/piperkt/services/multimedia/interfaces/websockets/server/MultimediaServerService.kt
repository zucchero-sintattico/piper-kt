package piperkt.services.multimedia.interfaces.websockets.server

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import io.micronaut.context.annotation.ConfigurationProperties
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolEvent.*
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage
import piperkt.services.multimedia.interfaces.websockets.on

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = 8888
}

class MultimediaSocketIOServer(
    private val sessionRepository: SessionRepository,
    private val configuration: SocketIOConfiguration = SocketIOConfiguration(),
) {
    private val socketIoConfig =
        Configuration().apply { port = configuration.port }.apply { isNeedClientAuth = false }
    private val server = SocketIOServer(socketIoConfig)
    private val clients = mutableMapOf<String, SocketIOClient>()
    private val clientToSessionId = mutableMapOf<String, String>()
    val events = mutableListOf<Any>()

    fun start() {
        server.addConnectListener(this::onConnect)
        server.addDisconnectListener(this::onDisconnect)
        server.on(JOIN.event) { client, message: MultimediaProtocolMessage.JoinMessage, _ ->
            events.add(message)
            onJoin(client, message)
        }
        server.on(OFFER.event) { _, message: MultimediaProtocolMessage.OfferMessage, _ ->
            events.add(message)
            onOffer(message)
        }
        server.on(ANSWER.event) { _, message: MultimediaProtocolMessage.AnswerMessage, _ ->
            events.add(message)
            onAnswer(message)
        }
        server.on(ICE_CANDIDATE.event) {
            _,
            message: MultimediaProtocolMessage.IceCandidateMessage,
            _ ->
            events.add(message)
            onIceCandidate(message)
        }
        server.start()
    }

    private fun SocketIOClient.getUsername(): String? {
        val token = this.handshakeData.httpHeaders.get("authToken")
        return token
    }

    private fun roomOf(sessionId: String) = server.getRoomOperations(sessionId)

    private fun SocketIOClient.notAuthenticated() {
        println("Not authenticated")
        this.sendEvent(NOT_AUTHENTICATED.event)
        this.disconnect()
    }

    private fun SocketIOClient.error(message: String) {
        println("Error: $message")
        this.sendEvent("error", message)
        this.disconnect()
    }

    fun onConnect(client: SocketIOClient) {
        val username = client.getUsername() ?: return client.notAuthenticated()
        clients[username] = client
        println("User $username connected")
    }

    fun onDisconnect(client: SocketIOClient) {
        val username = client.getUsername() ?: return client.notAuthenticated()
        clients.remove(username)
        println("User $username disconnected")
        clientToSessionId[username]?.let { sessionId ->
            client.leaveRoom(sessionId)
            roomOf(sessionId).sendEvent(USER_LEFT.event, username)
            val session =
                sessionRepository.findById(SessionId(sessionId))
                    ?: return client.error("Session not found")
            session.removeParticipant(Username(username))
            sessionRepository.save(session)
            println("User $username left session $sessionId")
        }
    }

    fun onJoin(
        client: SocketIOClient,
        joinMessage: MultimediaProtocolMessage.JoinMessage,
    ) {
        val username = client.getUsername() ?: return client.notAuthenticated()
        val sessionId = joinMessage.sessionId
        clientToSessionId[username] = sessionId
        roomOf(sessionId).sendEvent(USER_JOIN.event, username)
        client.joinRoom(sessionId)
        val session =
            sessionRepository.findById(SessionId(sessionId))
                ?: return client.error("Session not found")
        session.addParticipant(Username(username))
        sessionRepository.save(session)
        println("User $username joined session $sessionId")
    }

    fun onOffer(
        offerMessage: MultimediaProtocolMessage.OfferMessage,
    ) {
        val to = offerMessage.to
        println("User ${offerMessage.from} sent offer to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent(OFFER_RECEIVED.event, offerMessage)
    }

    fun onAnswer(
        answerMessage: MultimediaProtocolMessage.AnswerMessage,
    ) {
        val to = answerMessage.to
        println("User ${answerMessage.from} sent answer to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent(ANSWER_RECEIVED.event, answerMessage)
    }

    fun onIceCandidate(
        iceCandidateMessage: MultimediaProtocolMessage.IceCandidateMessage,
    ) {
        val to = iceCandidateMessage.to
        println("User ${iceCandidateMessage.from} sent ice candidate to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent(ICE_CANDIDATE_RECEIVED.event, iceCandidateMessage)
    }
}
