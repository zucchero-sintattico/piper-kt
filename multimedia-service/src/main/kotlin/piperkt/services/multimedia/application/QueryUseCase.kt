package piperkt.services.multimedia.application

interface QueryUseCase<in Q, out R> {

    fun execute(query: Q): R

    operator fun invoke(query: Q): Result<R> = runCatching { execute(query) }
}

interface CommandUseCase<in C> {

    fun execute(command: C)

    operator fun invoke(command: C): Result<Unit> = runCatching { execute(command) }
}
