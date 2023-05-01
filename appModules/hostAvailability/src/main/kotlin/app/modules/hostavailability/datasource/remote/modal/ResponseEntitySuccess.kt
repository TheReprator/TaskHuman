package app.modules.hostavailability.datasource.remote.modal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "success",
    "message"
)
open class ResponseEntitySuccess(
    @JsonProperty("success")
    val success: Boolean?,
    @JsonProperty("message")
    val message: String?
)