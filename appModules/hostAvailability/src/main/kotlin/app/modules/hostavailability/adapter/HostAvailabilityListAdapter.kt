package app.modules.hostavailability.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import app.modules.hostavailability.databinding.ItemHostAvailabilityBinding
import app.modules.hostavailability.di.HostAvailabilityModule
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.actions.DateUtils
import app.reprator.base_android.util.GeneralDiffUtil
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Named

class HostAvailabilityListAdapter @Inject constructor(
    @Named(HostAvailabilityModule.NAME_SCOPE) private val coroutineScope: CoroutineScope,
    private val itemCallback: HostAvailabilityItemCallback,
    private val dateUtils: DateUtils
) :
    ListAdapter<ModalHostItem, VHHostAvailability>(GeneralDiffUtil<ModalHostItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHHostAvailability {
        val binding = ItemHostAvailabilityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VHHostAvailability(binding, coroutineScope, itemCallback, dateUtils)
    }

    override fun onBindViewHolder(holder: VHHostAvailability, position: Int) {
        holder.binding.hostAvailabilityRootSwipe.addListener(holder.swipeListener)
        holder.bindItem(getItem(position))
    }

    override fun onViewRecycled(holder: VHHostAvailability) {
        holder.binding.hostAvailabilityRootSwipe.removeListener(holder.swipeListener)
        super.onViewRecycled(holder)
    }
}

