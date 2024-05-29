package piperkt.services.multimedia.interfaces.websockets

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
