package app.modules.hostavailability.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import app.modules.hostavailability.databinding.ItemHostAvailabilityBinding
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.actions.DateUtils
import app.reprator.base_android.util.GeneralDiffUtil
import javax.inject.Inject

class HostAvailabilityListAdapter @Inject constructor(private val dateUtils: DateUtils) :
    ListAdapter<ModalHostItem, VHHostAvailability>(GeneralDiffUtil<ModalHostItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHHostAvailability {
        val binding = ItemHostAvailabilityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VHHostAvailability(binding, dateUtils)
    }

    override fun onBindViewHolder(holder: VHHostAvailability, position: Int) {
        holder.bindItem(getItem(position))
    }

}
