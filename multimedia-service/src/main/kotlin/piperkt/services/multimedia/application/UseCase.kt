package piperkt.services.multimedia.application

interface UseCase<in I, out O>

interface QueryUseCase<in Q, out R> : UseCase<Q, R> {
    fun validate(query: Q)

    fun execute(query: Q): R

    operator fun invoke(query: Q): Result<R> = runCatching {
        validate(query)
        execute(query)
    }
}

interface CommandUseCase<in C> : UseCase<C, Unit> {
    fun validate(command: C)

    fun execute(command: C)

    operator fun invoke(command: C): Result<Unit> = runCatching {
        validate(command)
        execute(command)
    }
}
