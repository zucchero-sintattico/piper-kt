package piperkt.bdd

/*
// Enable only in test
@Singleton
@Requires(env = [Environment.TEST])
class MockedAuthenticationProvider : HttpRequestAuthenticationProvider<String> {
    override fun authenticate(
        requestContext: HttpRequest<String>?,
        authRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        return AuthenticationResponse.success(authRequest.identity)
    }
}

fun authOf(username: String): String {
    val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
    return req.headers.get(HttpHeaders.AUTHORIZATION)!!
}
*/
