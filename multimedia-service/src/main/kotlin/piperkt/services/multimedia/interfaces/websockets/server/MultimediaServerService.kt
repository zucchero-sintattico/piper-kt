package piperkt.services.multimedia.interfaces.websockets.server

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import io.micronaut.context.annotation.ConfigurationProperties
import jakarta.inject.Singleton
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaServerApi

class MultimediaServerService : MultimediaServerApi {

    override fun onUserJoin(username: String, sessionId: String) {
        TODO("Not yet implemented")
    }

    override fun onUserLeave(username: String, sessionId: String) {
        TODO("Not yet implemented")
    }

    override fun onOffer(username: String, sessionId: String, offer: String) {
        TODO("Not yet implemented")
    }

    override fun onAnswer(username: String, sessionId: String, answer: String) {
        TODO("Not yet implemented")
    }

    override fun onIceCandidate(username: String, sessionId: String, candidate: String) {
        TODO("Not yet implemented")
    }
}

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = 8888
}

@Serializable data class JoinMessage(val username: String, val content: String)

@Singleton
class MultimediaSocketIOServer(private val configuration: SocketIOConfiguration) {
    // Setup the server to use JSON for messages
    private val socketIoConfig = Configuration().apply { port = configuration.port }
    private val server = SocketIOServer(socketIoConfig)

    fun start() {
        server.addConnectListener { client -> println("connected: $client") }
        server.addDisconnectListener { println("disconnect") }
        server.addEventListener("join", String::class.java) { client, data, ackRequest ->
            val joinMessage = Json.decodeFromString(JoinMessage.serializer(), data)
            println("join: $joinMessage")
        }
        server.addEventListener("leave", String::class.java) { client, data, ackRequest ->
            println("leave")
        }
        server.start()
    }
}
