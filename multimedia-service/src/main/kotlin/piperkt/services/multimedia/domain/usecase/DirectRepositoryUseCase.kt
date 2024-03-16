package piperkt.services.multimedia.domain.usecase

import piperkt.services.multimedia.domain.directs.DirectRepository

interface DirectRepositoryUseCase {
    val directRepository: DirectRepository
}
