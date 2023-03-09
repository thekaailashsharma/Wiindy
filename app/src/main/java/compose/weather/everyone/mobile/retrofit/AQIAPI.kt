package compose.weather.everyone.mobile.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AQIAPI {
    @GET
    suspend fun getAQI(
        @Url Url: String,
    ): Response<aqi>
}