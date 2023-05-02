package app.modules.hostavailability.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.modules.hostavailability.adapter.HostAvailabilityItemCallback
import app.reprator.navigation.AppNavigator
import app.reprator.navigation.HostAvailabilityNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class HostAvailabilityModule {

    companion object {
        const val NAME_SCOPE = "HostAvailabilityScope"
    }

    @Provides
    fun provideHostAvailabilityNavigator(
        appNavigator: AppNavigator
    ): HostAvailabilityNavigator = appNavigator

    @Provides
    fun provideHostAvailabilityItemCallback(
        fragment: Fragment
    ): HostAvailabilityItemCallback = fragment as HostAvailabilityItemCallback

    @Named(NAME_SCOPE)
    @Provides
    fun provideFragmentCoroutineScope(
        fragment: Fragment
    ): CoroutineScope = fragment.lifecycleScope
}
