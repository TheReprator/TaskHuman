package app.modules.hostavailability.datasource.remote


import app.modules.hostavailability.datasource.remote.modal.EntityHostAvailability
import app.modules.hostavailability.datasource.remote.modal.RequestEntityRemoveFavourite
import app.modules.hostavailability.datasource.remote.modal.RequestEntiyFavourite
import app.modules.hostavailability.datasource.remote.modal.ResponseEntitySuccess
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HostAvailabilityApiService {
    @GET("v0.8/discover/topicDetails/physical fitness")
    suspend fun hostAvailabilityResponse(): Response<EntityHostAvailability>

    @POST("v0.8/favorite/add")
    suspend fun addFavourite(@Body entityFavourite: RequestEntiyFavourite): Response<ResponseEntitySuccess>

    @POST("v0.8/favorite/remove")
    suspend fun removeFavourite(@Body removeFavourite: RequestEntityRemoveFavourite): Response<ResponseEntitySuccess>


    /*
  'https://api-devenv.taskhuman.com/v0.8/favorite/add'
{
    "skillName": "Strength Training",
    "dictionaryName": "Physical fitness"
}
Success response:
{
    "success": true,
    "favorite": {
        "providerid": null,
        "favoriteBy": "skillOnly",
        "dictionaryName": "Physical fitness",
        "discoverItemId": null,
        "consumerGroup": null,
        "providerGroup": null,
        "_id": "644eb4e3fbb817e1f665e71c",
        "categoryName": "Hatha Yoga",
        "consumerid": 4716,
        "createdAt": "2023-04-30T18:35:15.039Z",
        "updatedAt": "2023-04-30T18:35:15.039Z",
        "__v": 0
    },
    "message": "Skills updated in your favorite list",
    "showFavoriteToast": true
}







https://api-devenv.taskhuman.com/v0.8/favorite/remove
{
  "skillName": "Strength Training"
}'



Success Response:
{
    "success": true,
    "message": "Remove favorite successful."
}




Failed:
{
    "errors": [
        {
            "reason": "Favorite skill does not exist"
        }
    ],
    "code": 400
}
    * */
}
