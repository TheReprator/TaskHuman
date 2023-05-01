package app.modules.hostavailability.data.repositoryImpl

import app.modules.hostavailability.data.datasource.HostAvailabilityRemoteDataSource
import app.modules.hostavailability.domain.repository.HostAvailabilityRepository
import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.actions.ConnectionDetector
import app.reprator.base.useCases.AppError
import app.reprator.base.useCases.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class HostAvailabilityDataRepositoryImpl @Inject constructor(
    private val connectionDetector: ConnectionDetector,
    private val hostAvailabilityRemoteDataSource: HostAvailabilityRemoteDataSource
) : HostAvailabilityRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun getHostAvailabilityRepository(): Flow<AppResult<ModalHostContainer>> {
        return if (connectionDetector.isInternetAvailable)
            flowOf(hostAvailabilityRemoteDataSource.getHostAvailabilityRemoteDataSource())
        else {
            flowOf(AppError(message = NO_INTERNET))
        }
    }

    override suspend fun markHostAsFavourite(skillName: String, dictionaryType: String): Flow<AppResult<Boolean>> {
        return if (connectionDetector.isInternetAvailable)
            flowOf(hostAvailabilityRemoteDataSource.markHostAsFavourite(skillName, dictionaryType))
        else {
            flowOf(AppError(message = NO_INTERNET))
        }
    }

    override suspend fun removeHostAsFavourite(skillName: String): Flow<AppResult<Boolean>> {
        return if (connectionDetector.isInternetAvailable)
            flowOf(hostAvailabilityRemoteDataSource.removeHostAsFavourite(skillName))
        else {
            flowOf(AppError(message = NO_INTERNET))
        }
    }
}