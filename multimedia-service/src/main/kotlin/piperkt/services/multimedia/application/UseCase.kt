package piperkt.services.multimedia.application

interface QueryUseCase<in Q, out R> {
    operator fun invoke(query: Q): Result<R>
}

interface CommandUseCase<in C> {
    operator fun invoke(command: C): Result<Unit>
}
