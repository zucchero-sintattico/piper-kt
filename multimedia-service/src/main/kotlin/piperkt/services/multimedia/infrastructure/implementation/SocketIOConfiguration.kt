package piperkt.services.multimedia.infrastructure.implementation

import io.micronaut.context.annotation.ConfigurationProperties
import kotlin.random.Random
import kotlin.random.nextInt

@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = Random.nextInt(10000..20000)
}
