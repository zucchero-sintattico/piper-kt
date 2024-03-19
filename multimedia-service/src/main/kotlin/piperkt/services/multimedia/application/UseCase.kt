package piperkt.services.multimedia.application

interface QueryUseCase<in Q, out R> {
    operator fun invoke(query: Q): Result<R>
}

interface CommandUseCase<in C> {
    operator fun invoke(command: C): Result<Unit>
}

interface AuthenticatedCommandUseCase<in C> {
    operator fun invoke(author: String, command: C): Result<Unit>
}

interface AuthenticatedQueryUseCase<in Q, out R> {
    operator fun invoke(author: String, query: Q): Result<R>
}
