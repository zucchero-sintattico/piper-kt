package piperkt.services.multimedia.interfaces.websockets

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import io.micronaut.context.annotation.ConfigurationProperties
import kotlin.random.Random
import kotlin.random.nextInt
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.JoinSession
import piperkt.services.multimedia.application.session.SessionService.Command.LeaveSession
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolEvent.*
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolMessage.UserJoined

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = Random.nextInt(10000..20000)
}

class MultimediaSocketIOServer(
    private val sessionService: SessionService,
    private val objectMapper: JsonMapper,
    val configuration: SocketIOConfiguration = SocketIOConfiguration(),
) {

    private val socketIoConfig =
        Configuration().apply { port = configuration.port }.apply { isNeedClientAuth = false }
    private val server = SocketIOServer(socketIoConfig)
    private val clients = mutableMapOf<String, SocketIOClient>()
    private val clientToSessionId = mutableMapOf<String, String>()
    val events = mutableListOf<Any>()

    fun start() {
        server.addConnectListener { client -> runCatching { onConnect(client) } }
        server.addDisconnectListener { client -> runCatching { onDisconnect(client) } }
        server.on(objectMapper, JOIN.event) {
            client,
            message: MultimediaProtocolMessage.JoinMessage,
            _,
            ->
            events.add(message)
            runCatching { onJoin(client, message) }
        }
        server.on(objectMapper, OFFER.event) {
            _,
            message: MultimediaProtocolMessage.OfferMessage,
            _,
            ->
            events.add(message)
            runCatching { onOffer(message) }
        }
        server.on(objectMapper, ANSWER.event) {
            _,
            message: MultimediaProtocolMessage.AnswerMessage,
            _,
            ->
            events.add(message)
            runCatching { onAnswer(message) }
        }
        server.on(objectMapper, ICE_CANDIDATE.event) {
            _,
            message: MultimediaProtocolMessage.IceCandidateMessage,
            _,
            ->
            events.add(message)
            runCatching { onIceCandidate(message) }
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
            val serialized = objectMapper.toJson(MultimediaProtocolMessage.UserLeft(username))
            roomOf(sessionId).sendEvent(USER_LEFT.event, serialized)
            sessionService.leaveSession(LeaveSession(SessionId(sessionId), Username(username)))
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
        val serialized = objectMapper.toJson(UserJoined(username))
        roomOf(sessionId).sendEvent(USER_JOIN.event, serialized)
        client.joinRoom(sessionId)
        sessionService.joinSession(JoinSession(SessionId(sessionId), Username(username)))
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
