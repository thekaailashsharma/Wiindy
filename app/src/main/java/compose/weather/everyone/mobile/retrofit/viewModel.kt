package compose.weather.everyone.mobile.retrofit

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import compose.weather.everyone.mobile.database.DatabaseDb
import compose.weather.everyone.mobile.database.DatabaseRepo
import compose.weather.everyone.mobile.database.WeatherEntity
import compose.weather.everyone.mobile.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    private val locationTracker: LocationTracker,
    application: Application
) : AndroidViewModel(application) {

    private val weatherRepository: DatabaseRepo

    init {

        val DB = DatabaseDb.getInstance(application)
        val dataDao = DB.weatherDao()
        weatherRepository = DatabaseRepo(dataDao)
    }


    var weatherResponse: MutableLiveData<NetworkResult<weather>> = MutableLiveData()
    var AQIResponse: MutableLiveData<NetworkResult<aqi>> = MutableLiveData()
    var geoResponse: MutableLiveData<NetworkResult<ReverseGeocoding>> = MutableLiveData()



    fun getWeather() {
        viewModelScope.launch {
            getWeatherSafeCall()
        }
    }
    fun getAQI(Url:String) {
        viewModelScope.launch {
            getAQISafeCall(Url)
        }
    }
    fun getGeo() {
        viewModelScope.launch {
            getGeoSafeCall()
        }
    }
    private suspend fun getAQISafeCall(Url: String) {
        if (checkInternetConnection()) {
            val aqiresponse = repository.remote.getAqi(Url)
            AQIResponse.value = handleAQIResponse(aqiresponse)
        } else {
            weatherResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleAQIResponse(response: Response<aqi>): NetworkResult<aqi>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Time Out")
            }
            response.isSuccessful -> {


                val aqiresponse = response.body()
                NetworkResult.Success(data = aqiresponse!!)
            }
            else -> {
                NetworkResult.Error(message = "Could Not Fetch Results")
            }
        }
    }

    private suspend fun getGeoSafeCall() {
        locationTracker.getCurrentLocation()?.let {
            if (checkInternetConnection()) {
                var latitude = it.latitude
                var longitude = it.longitude
                val GeoResponse = repository.remote.getGeo(
                    Url ="https://api.geoapify.com/v1/geocode/reverse?lat=$latitude&lon=" +
                            "$longitude&apiKey=d54a9410d1b24fa4a4a3f58579b57892"
                )
                geoResponse.value = handleGeoResponse(GeoResponse)
            } else {
                weatherResponse.value = NetworkResult.Error(message = "No Internet Connection")
            }
        }
    }

    private fun handleGeoResponse(response: Response<ReverseGeocoding>): NetworkResult<ReverseGeocoding>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Time Out")
            }
            response.isSuccessful -> {


                val geoResponse = response.body()
                NetworkResult.Success(data = geoResponse!!)
            }
            else -> {
                NetworkResult.Error(message = "Could Not Fetch Results")
            }
        }
    }





    private suspend fun getWeatherSafeCall() {
        locationTracker.getCurrentLocation()?.let {
            if (checkInternetConnection()) {
                var latitude = it.latitude
                var longitude = it.longitude
                val response = repository.remote.getWeather(latitude, longitude)
                weatherResponse.value = handleWeatherResponse(response, latitude, longitude)
            } else {
                weatherResponse.value = NetworkResult.Error(message = "No Internet Connection")
            }
        }
    }

    private fun handleWeatherResponse(
        response: Response<weather>,
        latitude: Double,
        longitude: Double,
    ): NetworkResult<weather>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Time Out")
            }
            response.isSuccessful -> {
                val weatherResponse = response.body()
//                viewModelScope.launch {
//                    weatherRepository.insertWeather(
//                        WeatherEntity(
//                            latitude = latitude,
//                            longitude = longitude,
//                            code = weatherResponse!!.current_weather.weathercode
//                        )
//                    )
//                }
                NetworkResult.Success(data = weatherResponse!!)
            }
            else -> {
                NetworkResult.Error(message = "Could Not Fetch Results")
            }
        }
    }

    fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}


