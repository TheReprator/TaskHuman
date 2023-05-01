package app.modules.hostavailability.domain.usecase

import app.modules.hostavailability.domain.repository.HostAvailabilityRepository
import app.reprator.base.useCases.AppResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HostAvailabilityFavouriteUseCase @Inject constructor(
    private val hostAvailabilityRepository: HostAvailabilityRepository
) {
    suspend operator fun invoke(skillName: String, dictionaryType: String): Flow<AppResult<Boolean>> {
        return hostAvailabilityRepository.markHostAsFavourite(skillName, dictionaryType)
    }

    suspend operator fun invoke(skillName: String): Flow<AppResult<Boolean>> {
        return hostAvailabilityRepository.removeHostAsFavourite(skillName)
    }
}