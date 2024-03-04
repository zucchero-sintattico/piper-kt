package micronaut.playground.infrastructure.websocket

import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket

@ServerWebSocket("/ws")
class WebsocketServer {
    @OnOpen
    fun onOpen() {
        println("Client connected")
    }

    @OnMessage
    fun onMessage(message: String) {
        println("Received message: $message")
    }
}
