package app.modules.hostavailability.modal

data class ModalHostContainer(
    val header: ModalHeader,
    val itemList: List<ModalHostItem>
)

data class ModalHeader(val headerTitle: String, val color: String)

data class ModalHostItem(
    val requestSkillName: String,
    val requestDictionaryName: String,
    val title: String,
    val isFavourite: Boolean,
    val availability: ModalAvailability,
    val participantUserList: List<String>,
    val isSwipeMenuOpened: Boolean = false,
    val showShortMessage: Boolean = false
)

data class ModalAvailability(
    val color: String,
    val startTime: Long,
    val endTime: Long
)