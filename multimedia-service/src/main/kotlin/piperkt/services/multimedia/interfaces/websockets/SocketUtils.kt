package piperkt.services.multimedia.interfaces.websockets

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

inline fun <reified E> E.toJson(): String = Json.encodeToString(serializer(), this)

inline fun <reified E> fromJson(json: String): E = Json.decodeFromString(serializer(), json)

inline fun <reified E> SocketIOServer.on(event: String, listener: DataListener<E>) {
    this.addEventListener(event, String::class.java) { client, data, ackRequest ->
        val message = fromJson<E>(data)
        listener.onData(client, message, ackRequest)
    }
}
