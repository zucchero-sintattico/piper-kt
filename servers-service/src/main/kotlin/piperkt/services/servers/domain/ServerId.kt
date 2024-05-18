package piperkt.services.servers.domain

import piperkt.common.ddd.UUIDEntityId

class ServerId(value: String = newId()) : UUIDEntityId(value)
