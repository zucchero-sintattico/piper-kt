package piperkt.services.multimedia.interfaces.websockets.server

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import io.micronaut.context.annotation.ConfigurationProperties
import jakarta.inject.Singleton
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

@Singleton
class MultimediaSocketIOServer(private val configuration: SocketIOConfiguration) {
    private val socketIoConfig = Configuration().apply { port = configuration.port }
    private val server = SocketIOServer(socketIoConfig)

    fun start() {
        server.addConnectListener { println("connect") }
        server.addDisconnectListener { println("disconnect") }
        server.addEventListener("join", String::class.java) { client, data, ackRequest ->
            println("join")
        }
        server.addEventListener("leave", String::class.java) { client, data, ackRequest ->
            println("leave")
        }
        server.start()
    }
}
