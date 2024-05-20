package piperkt.common.ddd

interface ValueObject

open class EntityId<Id>(open val value: Id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as EntityId<*>

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

open class Entity<Id : EntityId<*>>(open val id: Id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Entity<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

open class AggregateRoot<Id : EntityId<*>>(id: Id) : Entity<Id>(id)

interface Factory<E : Entity<*>>

interface Repository<Id : EntityId<*>, E : AggregateRoot<Id>> {
    fun findById(id: Id): E?

    fun save(entity: E)

    fun deleteById(id: Id): E?

    fun update(entity: E)

    fun deleteAll()
}
