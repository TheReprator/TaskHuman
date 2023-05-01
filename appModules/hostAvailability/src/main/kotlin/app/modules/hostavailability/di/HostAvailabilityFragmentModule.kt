package app.modules.hostavailability.di

import androidx.fragment.app.Fragment
import app.modules.hostavailability.adapter.HostAvailabilityItemCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class HostAvailabilityModule {

    @Provides
    fun provideHostAvailabilityItemCallback(
        fragment: Fragment
    ): HostAvailabilityItemCallback = fragment as HostAvailabilityItemCallback
}
