package app.modules.hostavailability.datasource.remote.mapper

import app.modules.hostavailability.datasource.remote.modal.EntityHostAvailability
import app.modules.hostavailability.modal.ModalAvailability
import app.modules.hostavailability.modal.ModalHeader
import app.modules.hostavailability.modal.ModalHostContainer
import app.modules.hostavailability.modal.ModalHostItem
import app.reprator.base.util.Mapper
import javax.inject.Inject

class HostAvailabilityMapper @Inject constructor() :
    Mapper<EntityHostAvailability, ModalHostContainer> {

    override suspend fun map(from: EntityHostAvailability): ModalHostContainer {

        val itemList: List<ModalHostItem> = from.skills?.filter {
            it.type == "dictionary"
        }?.map {
            val participantImageList:List<String> = it.providerInfo?.filter { providerItem ->
                !providerItem.profileImage.isNullOrBlank()
            }?.map { providerItem ->
                providerItem.profileImage!!
            } ?: emptyList()

            ModalHostItem(it.tileName.orEmpty(),
                it.dictionaryName.orEmpty(),
                it.displayTileName.orEmpty(),
                it.isFavorite ?: false,
                ModalAvailability(it.availability?.color.orEmpty(),
                    it.availability?.startTime ?: -1,
                    it.availability?.endTime ?: -1),
                participantImageList)
        } ?: emptyList()

        val headerEntity = from.topicHeader
        val header = ModalHeader(headerEntity?.tileName.orEmpty(), headerEntity?.color.orEmpty())
        return ModalHostContainer(header, itemList)
    }
}