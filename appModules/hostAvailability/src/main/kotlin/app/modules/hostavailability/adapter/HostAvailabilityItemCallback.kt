package app.modules.hostavailability.adapter

interface HostAvailabilityItemCallback {
    fun markFavouriteOrUnFavourite(position: Int)

    fun swipeOpen(position: Int)

    fun swipeClose(position: Int)
}