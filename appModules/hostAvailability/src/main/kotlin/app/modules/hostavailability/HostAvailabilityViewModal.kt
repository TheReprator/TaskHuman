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
import app.modules.hostavailability.domain.usecase.HostAvailabilityFavouriteUseCase
import app.modules.hostavailability.domain.usecase.HostAvailabilityUseCase
import app.modules.hostavailability.modal.ModalHeader
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.extensions.computationalBlock
import app.reprator.base.useCases.AppError
import app.reprator.base.useCases.AppSuccess
import app.reprator.base.util.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HostAvailabilityViewModal @Inject constructor(
    private val hostAvailabilityUseCase: HostAvailabilityUseCase,
    private val favouriteUseCase: HostAvailabilityFavouriteUseCase,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers
) : ViewModel() {

    companion object {
        const val SECONDS_3 = 3000L
    }

    private val mutex = Mutex()

    private var swipeOpenedPosition = -1

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
                                swipeOpenedPosition = -1
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

    private fun isPositionExist(position: Int): Boolean {
        return ((0 <= position) && (position < hostAvailabilityList.value.size))
    }

    fun markOrRemoveItemAsFavourite(position: Int) {
        computationalBlock {
            if (!isPositionExist(position))
                return@computationalBlock

            val item = hostAvailabilityList.value[position]
            val useCase = if (item.isFavourite)
                favouriteUseCase(item.requestSkillName)
            else
                favouriteUseCase(item.requestSkillName, item.requestDictionaryName)

            useCase.flowOn(coroutineDispatcherProvider.io)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {

                        when (it) {
                            is AppSuccess -> {
                                showShortMessageForSuccess(position)
                            }

                            is AppError -> {
                                _swipeErrorMsg.value = it.message ?: it.throwable?.message ?: ""
                            }

                            else -> throw IllegalArgumentException("Illegal State")
                        }
                    }
                }
        }
    }

    private fun showShortMessageForSuccess(position: Int) {

        if (!isPositionExist(position))
            return

        val shouldShowShortMessage = position == swipeOpenedPosition

        fun updateSpecificItem(shouldShow: Boolean) {
            val updatedList = hostAvailabilityList.value.toMutableList()
            val selectedItem = updatedList[position]
            val updatedItem = if(shouldShow)
                selectedItem.copy(isFavourite = !selectedItem.isFavourite, showShortMessage = shouldShowShortMessage)
            else
                selectedItem.copy(showShortMessage = false)

            updatedList[position] = updatedItem
            updateListWithSynchronization(updatedList)
        }

        updateSpecificItem(true)

        if(!shouldShowShortMessage)
            return

        viewModelScope.launch {
            delay(SECONDS_3)
            updateSpecificItem(false)
        }
    }

    fun setSwipeIndex(position: Int, isOpen: Boolean) {
        computationalBlock {
            if (!isPositionExist(position))
                return@computationalBlock

            val updatedList = hostAvailabilityList.value.toMutableList()

            val updatedMenuItem: (itemPosition: Int, isOpen: Boolean) -> ModalHostItem = { itemPosition, isOpen ->
                updatedList[itemPosition].copy(isSwipeMenuOpened = isOpen)
            }

            if (isOpen) {
                if (swipeOpenedPosition == position)
                    return@computationalBlock

                if (-1 == swipeOpenedPosition) {
                    swipeOpenedPosition = position
                } else {
                    updatedList[swipeOpenedPosition] = updatedMenuItem(swipeOpenedPosition, false)
                    swipeOpenedPosition = position
                }
                updatedList[position] = updatedMenuItem(position, true)
            } else {
                if (-1 == swipeOpenedPosition)
                    return@computationalBlock

                if (swipeOpenedPosition == position)
                    swipeOpenedPosition = -1
                updatedList[position] = updatedMenuItem(position, false)
            }

            withContext(coroutineDispatcherProvider.main) {
                updateListWithSynchronization(updatedList)
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
