package piperkt.services.multimedia.application

interface QueryUseCase<in Q, out R> {
    fun handle(query: Q): Result<R>
}

interface CommandUseCase<in C> {
    fun handle(command: C): Result<Unit>
}
