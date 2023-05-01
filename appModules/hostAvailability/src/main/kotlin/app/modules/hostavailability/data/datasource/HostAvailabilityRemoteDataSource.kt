package app.modules.hostavailability.data.datasource

import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.useCases.AppResult

interface HostAvailabilityRemoteDataSource {
    suspend fun getHostAvailabilityRemoteDataSource(): AppResult<ModalHostContainer>
}