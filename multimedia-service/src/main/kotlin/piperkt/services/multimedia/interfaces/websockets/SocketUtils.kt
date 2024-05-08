package piperkt.services.multimedia.interfaces.websockets

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton

@Singleton
class JsonMapper(private val mapper: ObjectMapper) {
    fun <E> fromJson(json: String, clazz: Class<E>): E {
        return mapper.readValue(json, clazz)
    }

    fun toJson(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }
}

inline fun <reified E> SocketIOServer.on(
    mapper: JsonMapper,
    event: String,
    listener: DataListener<E>
) {
    this.addEventListener(event, String::class.java) { client, data, ackRequest ->
        val message = mapper.fromJson(data, E::class.java)
        listener.onData(client, message, ackRequest)
    }
}
