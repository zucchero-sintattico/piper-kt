package piperkt.services.multimedia.infrastructure.implementation

import io.micronaut.context.annotation.ConfigurationProperties
import kotlin.random.Random

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = Random.nextInt(MIN_RANDOM_PORT, MAX_RANDOM_PORT)

    companion object {
        const val MIN_RANDOM_PORT = 10000
        const val MAX_RANDOM_PORT = 20000
    }
}
