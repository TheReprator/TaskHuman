package app.modules.hostavailability.di

import androidx.fragment.app.Fragment
import app.modules.hostavailability.adapter.HostAvailabilityItemCallback
import app.reprator.navigation.AppNavigator
import app.reprator.navigation.HostAvailabilityNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class HostAvailabilityModule {

    @Provides
    fun provideHostAvailabilityNavigator(
        appNavigator: AppNavigator
    ): HostAvailabilityNavigator = appNavigator

    @Provides
    fun provideHostAvailabilityItemCallback(
        fragment: Fragment
    ): HostAvailabilityItemCallback = fragment as HostAvailabilityItemCallback
}
