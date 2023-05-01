package app.modules.hostavailability.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isGone
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import app.modules.hostavailability.R
import app.modules.hostavailability.databinding.ItemHostAvailabilityBinding
import app.modules.hostavailability.databinding.ItemParticipantPicBinding
import app.modules.hostavailability.modal.ModalAvailability
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.actions.DateUtils
import app.reprator.base_android.binding.imageLoad
import app.reprator.base_android.extensions.appColorStateList
import app.reprator.base_android.extensions.appDimension
import app.reprator.base_android.extensions.appString
import app.reprator.base_android.widgets.SwipeLayout
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class VHHostAvailability(
    val binding: ItemHostAvailabilityBinding,
    private val itemCallback: HostAvailabilityItemCallback, private val dateUtils: DateUtils
) : RecyclerView.ViewHolder(binding.root) {

    val swipeListener = object : SwipeLayout.Listener {

        override fun onSwipe(menuView: View, swipeOffset: Float) {
            super.onSwipe(menuView, swipeOffset)
        }

        override fun onSwipeStateChanged(menuView: View, newState: Int) {
            super.onSwipeStateChanged(menuView, newState)
        }

        override fun onMenuClosed(menuView: View) {
            itemCallback.swipeClose(bindingAdapterPosition)
        }

        override fun onMenuOpened(menuView: View) {
            itemCallback.swipeOpen(bindingAdapterPosition)
        }
    }

    private val viewContext: () -> Context = {
        binding.root.context
    }

    init {
        binding.hostAvailabilitySwipeIsFavourite.setOnClickListener {
            itemCallback.markFavouriteOrUnFavourite(bindingAdapterPosition)
        }

        setCornerForSwipe()
    }

    private fun setCornerForSwipe() {

        val cornerBackground:(shapeAppearanceModel: ShapeAppearanceModel,
                              fillColor: Int, view: View) -> Unit = { shapeAppearanceModel, fillColor, view ->

            val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
            materialShapeDrawable.fillColor = viewContext().appColorStateList(fillColor)
            ViewCompat.setBackground(view, materialShapeDrawable)
        }

        val corner = 16f

        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, corner)
            .build()

        cornerBackground(shapeAppearanceModel, R.color.item_gray, binding.hostAvailabilityRoot)

        val shapeAppearanceModelSwipe = ShapeAppearanceModel()
            .toBuilder()
            .setBottomRightCornerSize(corner)
            .setTopRightCornerSize(corner)
            .build()
        cornerBackground(shapeAppearanceModelSwipe, android.R.color.holo_red_dark, binding.hostAvailabilitySwipeRoot)
    }

    fun bindItem(hostItem: ModalHostItem) {
        binding.hostAvailabilityTitle.text = hostItem.title
        setAvailabilityTime(hostItem.availability)
        setParticipantList(hostItem.participantUserList)
        handleSwipeTextAndIcon(hostItem)

        val statusColor = if(hostItem.availability.color.isEmpty())
            Color.BLUE
        else
            Color.parseColor(hostItem.availability.color)
        binding.hostAvailabilityStatusView.setBackgroundColor(statusColor)
    }

    private fun handleSwipeTextAndIcon(hostItem: ModalHostItem) {
        val text = if(hostItem.showShortMessage) {
            if (hostItem.isFavourite)
                R.string.host_short_add
            else
                R.string.host_short_remove
        } else {
            if (hostItem.isFavourite)
                R.string.host_remove_favourite
            else
                R.string.host_add_favourite
        }

        val icon = if (hostItem.isFavourite)
            R.drawable.remove_favorite
        else
            R.drawable.icon_favourite

        binding.hostAvailabilitySwipeIsFavourite.setImageResource(icon)
        binding.hostAvailabilitySwipeText.text = viewContext().appString(text)
    }

    private fun setParticipantList(participantPicList: List<String>) {
        with(binding.hostAvailabilityParticipantPicContainer) {
            removeAllViews()

            val layoutInflator = viewContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            participantPicList.forEachIndexed { index, item ->
                val participantView = ItemParticipantPicBinding.inflate(layoutInflator, null, false).root

                val imageDimension = viewContext().appDimension(R.dimen.participant_dimension)
                val layoutParam = LinearLayout.LayoutParams(imageDimension, imageDimension)

                val startMargin = if(0 == index)
                    0
                else
                    viewContext().appDimension(R.dimen.participant_pic_startMargin)

                layoutParam.setMargins(startMargin, 0, 0, 0)
                participantView.layoutParams = layoutParam

                addView(participantView)

                participantView.imageLoad(item)
            }
        }
    }

    private fun setAvailabilityTime(item: ModalAvailability) {

        with(binding.hostAvailabilityTime) {

            if (-1L == item.startTime) {
                isGone = true
            } else {
                isGone = false

                val systemTime =
                    dateUtils.format(dateUtils.getCalendar().timeInMillis, DateUtils.DD_MMM_YYYY_HOUR_MINUTE_SECONDS)

                val hostStartTime = dateUtils.format(item.startTime, DateUtils.DD_MMM_YYYY_HOUR_MINUTE_SECONDS)
                val startTime = viewContext().appString(R.string.host_time_now).takeIf {
                    systemTime == hostStartTime
                } ?: dateUtils.format(item.startTime, DateUtils.HOUR_MINUTE)

                val endTime = dateUtils.format(item.endTime, DateUtils.HOUR_MINUTE)

                text = viewContext().resources.getString(R.string.host_availability, startTime, endTime)
            }
        }
    }
}