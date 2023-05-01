package app.modules.hostavailability.domain.repository

import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.useCases.AppResult
import kotlinx.coroutines.flow.Flow

interface HostAvailabilityRepository {
    suspend fun getHostAvailabilityRepository():
            Flow<AppResult<ModalHostContainer>>
}