package piperkt.services.users.interfaces.web.auth

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest

object TestUtils {
    fun authOf(username: String): String {
        val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
        return req.headers.get(HttpHeaders.AUTHORIZATION)!!
    }
}
