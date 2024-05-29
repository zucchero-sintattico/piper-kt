package piperkt.services.multimedia.interfaces.websockets

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener

inline fun <reified E> SocketIOServer.on(
    mapper: JsonMapper,
    event: String,
    listener: DataListener<E>,
) {
    this.addEventListener(event, String::class.java) { client, data, ackRequest ->
        val message = mapper.fromJson(data, E::class.java)
        listener.onData(client, message, ackRequest)
    }
}
