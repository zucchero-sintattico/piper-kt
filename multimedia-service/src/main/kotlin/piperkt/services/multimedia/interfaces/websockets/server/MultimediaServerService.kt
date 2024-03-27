package piperkt.services.multimedia.interfaces.websockets.server

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import io.micronaut.context.annotation.ConfigurationProperties
import piperkt.services.multimedia.interfaces.on
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = 8888
}

class MultimediaSocketIOServer(
    private val configuration: SocketIOConfiguration = SocketIOConfiguration()
) {
    private val socketIoConfig =
        Configuration().apply { port = configuration.port }.apply { isNeedClientAuth = false }
    private val server = SocketIOServer(socketIoConfig)
    private val clients = mutableMapOf<String, SocketIOClient>()
    val events = mutableListOf<Any>()

    fun start() {
        server.addConnectListener(this::onConnect)
        server.addDisconnectListener(this::onDisconnect)
        server.on("join") { client, message: MultimediaProtocolMessage.JoinMessage, _ ->
            onJoin(client, message)
            events.add(message)
        }
        server.on("offer") { _, message: MultimediaProtocolMessage.OfferMessage, _ ->
            onOffer(message)
            events.add(message)
        }
        server.on("answer") { _, message: MultimediaProtocolMessage.AnswerMessage, _ ->
            onAnswer(message)
            events.add(message)
        }
        server.on("candidate") { _, message: MultimediaProtocolMessage.IceCandidateMessage, _ ->
            onIceCandidate(message)
            events.add(message)
        }
        server.start()
    }

    private fun SocketIOClient.getUsername(): String? {
        val token = this.handshakeData.httpHeaders.get("authToken")
        return token
    }

    private fun roomOf(sessionId: String) = server.getRoomOperations(sessionId)

    private fun SocketIOClient.notAuthenticated() {
        this.sendEvent("notAuthenticated")
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
    }

    fun onJoin(
        client: SocketIOClient,
        joinMessage: MultimediaProtocolMessage.JoinMessage,
    ) {
        val username = client.getUsername() ?: return client.notAuthenticated()
        val sessionId = joinMessage.sessionId
        roomOf(sessionId).sendEvent("userJoin", username)
        client.joinRoom(sessionId)
        println("User $username joined session $sessionId")
    }

    fun onOffer(
        offerMessage: MultimediaProtocolMessage.OfferMessage,
    ) {
        val to = offerMessage.to
        println("User ${offerMessage.from} sent offer to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent("offer", offerMessage)
    }

    fun onAnswer(
        answerMessage: MultimediaProtocolMessage.AnswerMessage,
    ) {
        val to = answerMessage.to
        println("User ${answerMessage.from} sent answer to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent("answer", answerMessage)
    }

    fun onIceCandidate(
        iceCandidateMessage: MultimediaProtocolMessage.IceCandidateMessage,
    ) {
        val to = iceCandidateMessage.to
        println("User ${iceCandidateMessage.from} sent ice candidate to $to")
        val toClient = clients[to] ?: return
        toClient.sendEvent("iceCandidate", iceCandidateMessage)
    }
}
