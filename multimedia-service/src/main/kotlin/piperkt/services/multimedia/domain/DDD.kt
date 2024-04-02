package piperkt.services.multimedia.domain

open class EntityId<I>(val value: I) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId<*>

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

open class Entity<I : EntityId<*>>(open val id: I) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

open class AggregateRoot<I : EntityId<*>>(id: I) : Entity<I>(id)

interface ValueObject

interface Repository<I : EntityId<*>, E : AggregateRoot<I>> {
    fun findById(id: I): E?

    fun save(entity: E): E

    fun deleteById(id: I)
}
