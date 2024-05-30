package piperkt.services.multimedia.interfaces.websockets

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import com.google.gson.Gson

var gson = Gson()

inline fun <reified E> SocketIOServer.on(
    mapper: JsonMapper,
    event: String,
    listener: DataListener<E>,
) {
    this.addEventListener(event, Object::class.java) { client, data, ackRequest ->
        println("Received event $event with data $data")
        val message = gson.fromJson(data.toString(), E::class.java)
        println("Parsed message $message")
        listener.onData(client, message, ackRequest)
    }
}
