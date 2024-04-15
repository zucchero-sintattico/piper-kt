package piperkt.common

open class InMemoryRepository<I : EntityId<*>, A : AggregateRoot<I>>(
    var entities: Map<I, A> = mapOf()
) : Repository<I, A> {
    override fun findById(id: I): A? {
        return entities[id]
    }

    override fun save(entity: A) {
        entities += entity.id to entity
    }

    override fun deleteById(id: I): A? {
        val entity = findById(id)
        entities -= id
        return entity
    }

    fun clear() {
        entities = mapOf()
    }
}
