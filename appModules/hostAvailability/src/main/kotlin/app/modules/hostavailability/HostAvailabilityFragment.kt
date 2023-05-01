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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.modules.hostavailability.adapter.HostAvailabilityListAdapter
import app.modules.hostavailability.adapter.HostAvailabilityItemCallback
import app.modules.hostavailability.databinding.FragmentHostAvailabilityBinding
import app.reprator.base.actions.Logger
import app.reprator.base_android.util.ItemOffsetDecoration
import app.reprator.base_android.viewDelegation.viewBinding
import app.reprator.navigation.HostAvailabilityNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HostAvailabilityFragment : Fragment(R.layout.fragment_host_availability), HostAvailabilityItemCallback {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var hostAvailabilityNavigator: HostAvailabilityNavigator

    @Inject
    lateinit var hostAvailabilityListAdapter: HostAvailabilityListAdapter

    private val binding by viewBinding(FragmentHostAvailabilityBinding::bind)
    private val viewModel: HostAvailabilityViewModal by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            adapter = hostAvailabilityListAdapter
            viewModal = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUpRecyclerView()
        initializeObserver()

        if (null == savedInstanceState) {
            viewModel.fetchHostAvailability()
        }
    }

    private fun setUpRecyclerView() {

        with(binding.hostAvailabilityRecyclerView) {
            itemAnimator?.changeDuration = 0
            itemAnimator = null

            addItemDecoration(
                ItemOffsetDecoration(requireContext(), R.dimen.list_item_padding)
            )
        }
    }

    private fun initializeObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hostAvailabilityList.collect {
                    hostAvailabilityListAdapter.submitList(it)
                }
            }
        }

    }

    override fun markFavouriteOrUnFavourite(position: Int) {
        logger.e("vikram fav:: $position")
    }

    override fun swipeOpen(position: Int) {
        logger.e("vikram swipeOpen:: $position")
        viewModel.setSwipeIndex(position, true)
    }

    override fun swipeClose(position: Int) {
        logger.e("vikram swipeClose:: $position")
        viewModel.setSwipeIndex(position, false)
    }
}