package app.modules.hostavailability.data.datasource

import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.useCases.AppResult

interface HostAvailabilityRemoteDataSource {
    suspend fun getHostAvailabilityRemoteDataSource(): AppResult<ModalHostContainer>

    suspend fun markHostAsFavourite(skillName: String, dictionaryType: String): AppResult<Boolean>

    suspend fun removeHostAsFavourite(skillName: String): AppResult<Boolean>
}