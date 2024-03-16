package piperkt.services.friendship

import io.micronaut.runtime.Micronaut.run

fun main(vararg args: String) {
    run(*args)
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
