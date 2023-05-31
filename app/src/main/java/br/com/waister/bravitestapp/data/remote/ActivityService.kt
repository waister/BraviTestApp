package br.com.waister.bravitestapp.data.remote

import br.com.waister.bravitestapp.models.ActivityResponse
import retrofit2.Response
import retrofit2.http.*

interface ActivityService {

    @GET("/{type}")
    suspend fun getRandom(
        @Path("type") type: String
    ): Response<ActivityResponse>

}
