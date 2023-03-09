package compose.weather.everyone.mobile.retrofit


import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApi: api,
    private val aqiApi: AQIAPI,
    private val geoAPI: GeoAPI
) {

    suspend fun getWeather(latitude:Double, longitude:Double): Response<weather> {
        return weatherApi.getWeather(latitude, longitude)
    }
    suspend fun getAqi(Url:String): Response<aqi> {
        return aqiApi.getAQI(Url)
    }
    suspend fun getGeo(Url:String): Response<ReverseGeocoding> {
        return geoAPI.getGeo(Url)
    }
}
