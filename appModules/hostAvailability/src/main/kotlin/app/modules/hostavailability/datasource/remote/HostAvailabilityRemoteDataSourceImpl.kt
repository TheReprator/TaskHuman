package app.modules.hostavailability.datasource.remote

import app.modules.hostavailability.data.datasource.HostAvailabilityRemoteDataSource
import app.modules.hostavailability.datasource.remote.mapper.HostAvailabilityMapper
import app.modules.hostavailability.datasource.remote.modal.RequestEntityRemoveFavourite
import app.modules.hostavailability.datasource.remote.modal.RequestEntiyFavourite
import app.modules.hostavailability.modal.ModalHostContainer
import app.reprator.base.useCases.AppError
import app.reprator.base.useCases.AppResult
import app.reprator.base.useCases.AppSuccess
import app.reprator.base.util.network.safeApiCall
import app.reprator.base.util.network.toResult
import javax.inject.Inject

class HostAvailabilityRemoteDataSourceImpl @Inject constructor(
    private val apiService: HostAvailabilityApiService,
    private val mapper: HostAvailabilityMapper
) : HostAvailabilityRemoteDataSource {

    private suspend fun getHostAvailabilityRemoteDataSourceApi():
            AppResult<ModalHostContainer> {

        return when (val data = apiService.hostAvailabilityResponse().toResult()) {
            is AppSuccess -> {
                val result = data.data
                if(true == result.success)
                    AppSuccess(mapper.map(result))
                else
                    AppError(message = result.message)
            }

            is AppError -> {
                AppError(message = data.message, throwable = data.throwable)
            }

            else -> throw IllegalArgumentException("Illegal State")
        }
    }

    override suspend fun getHostAvailabilityRemoteDataSource(): AppResult<ModalHostContainer> =
        safeApiCall(call = { getHostAvailabilityRemoteDataSourceApi() })

    private suspend fun markHostAsFavouriteApi(entityFavourite: RequestEntiyFavourite):
            AppResult<Boolean> {

        return when (val data = apiService.addFavourite(entityFavourite).toResult()) {
            is AppSuccess -> {
                val result = data.data
                if(true == result.success)
                    AppSuccess(result.success)
                else
                    AppError(message = result.message)
            }

            is AppError -> {
                AppError(message = data.message, throwable = data.throwable)
            }

            else -> throw IllegalArgumentException("Illegal State")
        }
    }

    override suspend fun markHostAsFavourite(skillName: String, dictionaryType: String): AppResult<Boolean> {
        return safeApiCall(call = { markHostAsFavouriteApi(RequestEntiyFavourite(skillName, dictionaryType)) })
    }

    private suspend fun removeHostAsFavouriteApi(removeFavourite: RequestEntityRemoveFavourite):
            AppResult<Boolean> {

        return when (val data = apiService.removeFavourite(removeFavourite).toResult()) {
            is AppSuccess -> {
                val result = data.data
                if(true == result.success)
                    AppSuccess(result.success)
                else
                    AppError(message = result.message)
            }

            is AppError -> {
                AppError(message = data.message, throwable = data.throwable)
            }

            else -> throw IllegalArgumentException("Illegal State")
        }
    }

    override suspend fun removeHostAsFavourite(skillName: String): AppResult<Boolean> {
        return safeApiCall(call = { removeHostAsFavouriteApi(RequestEntityRemoveFavourite(skillName)) })
    }
}