package piperkt.services.servers

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

// fun main(vararg args: String) {
//    Micronaut.run(*args)
// }

@OpenAPIDefinition(
    info =
        Info(
            title = "Piper-kt",
            version = "1.0",
            description = "Piper-kt API",
            license = License(name = "Apache 2.0", url = "https://foo.bar"),
            contact =
                Contact(
                    url = "https://gigantic-server.com",
                    name = "Fred",
                    email = "Fred@gigagantic-server.com"
                )
        )
)
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(Application.javaClass)
    }
}
// LEVEL -> WHATS -> DEPENDENCIES
// --------------------------------
// DOMAIN -> Language:
// - Entities
// - Value Objects
// - Repositories
// - Factories
// APPLICATION -> Domain
// - Services
// INTERFACES ->
// - Controller (Indepedent of the framework)
// INFRASTRUCTURE
