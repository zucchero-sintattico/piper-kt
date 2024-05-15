package piperkt.services.servers

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "Piper-kt Servers API",
            version = "1.0",
            description = "Piper-kt Servers Service API",
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
        Micronaut.run(Application.javaClass)
    }
}
