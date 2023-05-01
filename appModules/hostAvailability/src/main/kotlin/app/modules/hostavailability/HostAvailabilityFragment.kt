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
import app.modules.hostavailability.databinding.FragmentHostAvailabilityBinding
import app.reprator.base_android.viewDelegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostAvailabilityFragment : Fragment(R.layout.fragment_host_availability) {


    private val binding by viewBinding(FragmentHostAvailabilityBinding::bind)
    private val viewModel: HostAvailabilityViewModal by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModal = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

}