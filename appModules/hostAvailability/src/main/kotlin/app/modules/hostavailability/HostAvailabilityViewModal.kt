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

package app.modules.hostavailability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.modules.hostavailability.domain.usecase.HostAvailabilityUseCase
import app.modules.hostavailability.modal.ModalHeader
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.extensions.computationalBlock
import app.reprator.base.useCases.AppError
import app.reprator.base.useCases.AppSuccess
import app.reprator.base.util.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HostAvailabilityViewModal @Inject constructor(
    private val hostAvailabilityUseCase: HostAvailabilityUseCase,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers
) : ViewModel() {

    private val mutex = Mutex()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg

    private val _hostAvailabilityList = MutableStateFlow(emptyList<ModalHostItem>())
    val hostAvailabilityList: StateFlow<List<ModalHostItem>> = _hostAvailabilityList

    private val _headerItem = MutableStateFlow(ModalHeader("",""))
    val headerItem: StateFlow<ModalHeader> = _headerItem

    private val _swipeLoading = MutableStateFlow(false)
    val swipeLoading: StateFlow<Boolean> = _swipeLoading

    private val _swipeErrorMsg = MutableStateFlow("")
    val swipeErrorMsg: StateFlow<String> = _swipeErrorMsg

    fun fetchHostAvailability() {
        useCaseCall(
            {
                _isLoading.value = it
            },
            {
                _errorMsg.value = it
            }
        )
    }

    fun retryHostAvailabilityList() {
        _isLoading.value = true
        _errorMsg.value = ""
        fetchHostAvailability()
    }

    fun onRefresh() {
        useCaseCall(
            {
                _swipeLoading.value = it
            },
            {
                _swipeErrorMsg.value = it
            }
        )
    }

    private fun useCaseCall(
        blockLoader: (Boolean) -> Unit,
        blockError: (String) -> Unit
    ) {

        computationalBlock {
            hostAvailabilityUseCase().flowOn(coroutineDispatcherProvider.io)
                .catch { e ->
                    blockError(e.localizedMessage ?: "")
                }.onStart {
                    blockLoader(true)
                }.onCompletion {
                    blockLoader(false)
                }.flowOn(coroutineDispatcherProvider.main)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {
                        blockLoader(false)

                        when (it) {
                            is AppSuccess -> {
                                _headerItem.value = it.data.header
                                updateListWithSynchronization(it.data.itemList)
                            }

                            is AppError -> {
                                blockError(it.message ?: it.throwable?.message ?: "")
                            }

                            else -> throw IllegalArgumentException("Illegal State")
                        }
                    }
                }
        }
    }

    private fun updateListWithSynchronization(list: List<ModalHostItem>) {
        viewModelScope.launch {
            mutex.withLock {
                _hostAvailabilityList.value = list
            }
        }
    }

    private fun computationalBlock(
        coroutineExceptionHandler: CoroutineExceptionHandler? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.computationalBlock(
            coroutineDispatcherProvider,
            coroutineExceptionHandler,
            block
        )
    }
}
