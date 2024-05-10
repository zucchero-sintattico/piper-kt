package piperkt.services.friendships

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "Piper-kt Friendships API",
            version = "1.0",
            description = "Piper-kt Friendships Service API",
            contact =
                Contact(
                    url = "https://piper-kt.com",
                    name = "Zucchero Sintattico",
                    email = "zuccherosintattico@gmail.com"
                )
        )
)
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        run(Application.javaClass)
    }
}
