package piperkt.services.users

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "Piper-kt Users API",
            version = "1.0",
            description = "Piper-kt Users Service API",
            contact =
                Contact(
                    url = "https://piper-kt.com",
                    name = "Zucchero Sintattico",
                    email = "zuccherosintattico@gmail.com"
                )
        )
)
object Application

fun main(vararg args: String) {
    run(*args)
}
