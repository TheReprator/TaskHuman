/*
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")

package dev.reprator.taskhuman.implementation.connectivity

import android.content.Context
import androidx.lifecycle.*
import app.reprator.base.actions.ConnectionDetector
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.reprator.taskhuman.implementation.connectivity.base.ConnectivityProvider
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetChecker @Inject constructor(
    @ApplicationContext private val context: Context,
    lifecycle: Lifecycle,
    override var isInternetAvailable: Boolean = false
) : DefaultLifecycleObserver, ConnectionDetector, ConnectivityProvider.ConnectivityStateListener {

    private var isSubscriptionAlreadyAdded = false

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(context) }

    init {
        lifecycle.addObserver(this@InternetChecker)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        isInternetAvailable = state.hasInternet()
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        isSubscriptionAlreadyAdded = true
        provider.addListener(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        if(!isSubscriptionAlreadyAdded)
            provider.addListener(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isSubscriptionAlreadyAdded = false
        provider.removeListener(this)
    }
}
