package piperkt.services.multimedia.application

import piperkt.services.multimedia.domain.Username

interface QueryUseCase<in Q, out R> {
    operator fun invoke(query: Q): Result<R>
}

interface CommandUseCase<in C> {
    operator fun invoke(command: C): Result<Unit>
}

interface AuthenticatedCommandUseCase<in C> {
    operator fun invoke(author: Username, command: C): Result<Unit>

    fun validate(author: Username, command: C): Result<Unit>
}

interface AuthenticatedQueryUseCase<in Q, out R> {
    operator fun invoke(author: Username, query: Q): Result<R>
}

interface Authored {
    var author: Username
}
