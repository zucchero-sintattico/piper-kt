package piperkt.bdd.utils

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.security.token.render.BearerAccessRefreshToken
import java.net.URL

abstract class AbstractHttpClient {
    val httpClient = HttpClient.create(URL("http://localhost:10000"))

    inline fun <reified T> POST(
        path: String,
        body: Any,
        token: BearerAccessRefreshToken? = null,
    ): T {
        return httpClient
            .toBlocking()
            .exchange(HttpRequest.POST(path, body).bearerAuth(token?.accessToken), T::class.java)
            .body()
    }

    inline fun <reified T> GET(path: String, token: BearerAccessRefreshToken? = null): T {
        return httpClient
            .toBlocking()
            .exchange(HttpRequest.GET<T>(path).bearerAuth(token?.accessToken), T::class.java)
            .body()
    }

    inline fun <reified T> PUT(
        path: String,
        body: Any,
        token: BearerAccessRefreshToken? = null,
    ): T {
        return httpClient
            .toBlocking()
            .exchange(HttpRequest.PUT(path, body).bearerAuth(token?.accessToken), T::class.java)
            .body()
    }

    inline fun <reified T> DELETE(path: String, token: BearerAccessRefreshToken? = null): T {
        return httpClient
            .toBlocking()
            .exchange(HttpRequest.DELETE<T>(path).bearerAuth(token?.accessToken), T::class.java)
            .body()
    }
}
