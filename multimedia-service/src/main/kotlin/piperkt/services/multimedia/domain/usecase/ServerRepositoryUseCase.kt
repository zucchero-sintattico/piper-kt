package piperkt.services.multimedia.domain.usecase

import piperkt.services.multimedia.domain.servers.ServerRepository

interface ServerRepositoryUseCase {
    val serverRepository: ServerRepository
}
