package app.modules.hostavailability.datasource.remote


import app.modules.hostavailability.datasource.remote.modal.EntityHostAvailability
import retrofit2.Response
import retrofit2.http.GET

interface HostAvailabilityApiService {
    @GET("v0.8/discover/topicDetails/physical fitness")
    suspend fun hostAvailabilityResponse(): Response<EntityHostAvailability>
}
