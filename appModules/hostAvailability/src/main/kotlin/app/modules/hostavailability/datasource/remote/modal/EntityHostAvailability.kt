package app.modules.hostavailability.datasource.remote.modal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "success",
    "topicHeader",
    "skills",
    "isNextPage",
    "message"
)
data class EntityHostAvailability(
    @JsonProperty("success")
    val success: Boolean?,
    @JsonProperty("topicHeader")
    val topicHeader: EntityTopicHeader?,
    @JsonProperty("skills")
    val skills: List<EntitySkill>?,
    @JsonProperty("isNextPage")
    val isNextPage: Boolean?,
    @JsonProperty("message")
    val message: String?
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "tileName",
    "type",
    "columns",
    "color",
    "topicIcon"
)
data class EntityTopicHeader(
    @JsonProperty("tileName")
    val tileName: String?,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("columns")
    val columns: Int?,
    @JsonProperty("color")
    val color: String?,
    @JsonProperty("topicIcon")
    val topicIcon: String?
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "tileName",
    "displayTileName",
    "type",
    "dictionaryName",
    "tileBackground",
    "skillIcon",
    "availability",
    "moreProvidersAvailable",
    "providerInfo",
    "isFavorite",
    "columns",
    "tileColor",
    "blogMetaData"
)
data class EntitySkill(

    @JsonProperty("tileName")
    val tileName: String?,
    @JsonProperty("displayTileName")
    val displayTileName: String?,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("dictionaryName")
    val dictionaryName: String?,
    @JsonProperty("tileBackground")
    val tileBackground: String?,
    @JsonProperty("skillIcon")
    val skillIcon: String?,
    @JsonProperty("availability")
    val availability: EntityAvailability?,
    @JsonProperty("moreProvidersAvailable")
    val moreProvidersAvailable: Boolean?,
    @JsonProperty("providerInfo")
    val providerInfo: List<EntityProviderInfoItem>?,
    @JsonProperty("isFavorite")
    val isFavorite: Boolean?,
    @JsonProperty("columns")
    val columns: Int?,
    @JsonProperty("tileColor")
    val tileColor: String?,
    @JsonProperty("blogMetaData")
    val blogMetaData: EntityBlogMetaData?
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "status",
    "color",
    "startTime",
    "endTime"
)
data class EntityAvailability(
    @JsonProperty("status")
    val status: Int?,
    @JsonProperty("color")
    val color: String?,
    @JsonProperty("startTime")
    val startTime: Long?,
    @JsonProperty("endTime")
    val endTime: Long?
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "providerId",
    "startTime",
    "endTime",
    "profileImage"
)
data class EntityProviderInfoItem(
    @JsonProperty("providerId")
    val providerId: Int?,
    @JsonProperty("startTime")
    val startTime: Long?,
    @JsonProperty("endTime")
    val endTime: Long?,
    @JsonProperty("profileImage")
    val profileImage: String?
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "_id",
    "estReadTime",
    "link",
    "title",
    "blogImageUrl",
    "providerInfo"
)
data class EntityBlogMetaData(
    @JsonProperty("_id")
    val id: String,
    @JsonProperty("estReadTime")
    val estReadTime: Long,
    @JsonProperty("link")
    val link: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("blogImageUrl")
    val blogImageUrl: String,
    @JsonProperty("providerInfo")
    val providerInfo: List<EntityBlogProviderInfoItem>?
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "firstName",
    "lastName",
    "userId",
    "imageUrl",
    "groupIcon",
    "group",
    "profileImage",
    "providerId"
)
data class EntityBlogProviderInfoItem(

    @JsonProperty("firstName")
    val firstName: String?,
    @JsonProperty("lastName")
    val lastName: String?,
    @JsonProperty("userId")
    val userId: Int?,
    @JsonProperty("imageUrl")
    val imageUrl: String?,
    @JsonProperty("groupIcon")
    val groupIcon: String?,
    @JsonProperty("group")
    val group: String?,
    @JsonProperty("profileImage")
    val profileImage: String?,
    @JsonProperty("providerId")
    val providerId: Int?
)