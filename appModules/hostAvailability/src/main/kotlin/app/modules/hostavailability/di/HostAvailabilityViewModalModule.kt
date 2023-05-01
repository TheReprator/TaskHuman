package app.modules.hostavailability.di

import app.modules.hostavailability.data.datasource.HostAvailabilityRemoteDataSource
import app.modules.hostavailability.data.repositoryImpl.HostAvailabilityDataRepositoryImpl
import app.modules.hostavailability.datasource.remote.HostAvailabilityApiService
import app.modules.hostavailability.datasource.remote.HostAvailabilityRemoteDataSourceImpl
import app.modules.hostavailability.domain.repository.HostAvailabilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class HostAvailabilityViewModalModule {

    @Provides
    fun provideHostAvailabilityRemoteDataSource(
        hostAvailabilityRemoteDataSourceImpl: HostAvailabilityRemoteDataSourceImpl
    ): HostAvailabilityRemoteDataSource = hostAvailabilityRemoteDataSourceImpl

    @Provides
    fun provideHostAvailabilityRepository(
        hostAvailabilityDataRepositoryImpl: HostAvailabilityDataRepositoryImpl
    ): HostAvailabilityRepository = hostAvailabilityDataRepositoryImpl

    @Provides
    fun provideFactListApiService(
        retrofit: Retrofit
    ): HostAvailabilityApiService {
        return retrofit
            .create(HostAvailabilityApiService::class.java)
    }
}