package data

import piperkt.services.multimedia.domain.AggregateRoot
import piperkt.services.multimedia.domain.EntityId
import piperkt.services.multimedia.domain.Repository

open class InMemoryRepository<I : EntityId<*>, A : AggregateRoot<I>>(
    var entities: Map<I, A> = mapOf()
) : Repository<I, A> {
    override fun findById(id: I): A? {
        return entities[id]
    }

    override fun save(entity: A): A {
        entities += entity.id to entity
        return entity
    }

    override fun deleteById(id: I) {
        entities -= id
    }

    fun clear() {
        entities = mapOf()
    }
}
