package compose.weather.everyone.mobile.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GeoAPI {
    @GET
    suspend fun getGeo(
        @Url Url: String,
    ): Response<ReverseGeocoding>
}