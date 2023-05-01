package app.modules.hostavailability.domain.usecase

import app.modules.hostavailability.domain.repository.HostAvailabilityRepository
import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.useCases.AppResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HostAvailabilityUseCase @Inject constructor(
    private val hostAvailabilityRepository: HostAvailabilityRepository
) {
    suspend operator fun invoke(): Flow<AppResult<ModalHostContainer>> {
        return hostAvailabilityRepository.getHostAvailabilityRepository()
    }
}