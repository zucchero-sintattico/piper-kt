package piperkt.services.multimedia.application

interface UseCase<in Q, out R> {
    fun handle(query: Q): Result<R>
}
