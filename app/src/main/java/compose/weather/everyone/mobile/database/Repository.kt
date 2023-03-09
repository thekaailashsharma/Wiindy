package compose.weather.everyone.mobile.database

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import compose.weather.everyone.mobile.retrofit.NetworkResult
import compose.weather.everyone.mobile.retrofit.api
import compose.weather.everyone.mobile.retrofit.weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DatabaseRepo(private val weatherDao: WeatherDao) {
    val getWeather: Flow<List<WeatherEntity>> = weatherDao.getWeather()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun insertWeather(weather: WeatherEntity) {
        coroutineScope.launch {
            weatherDao.insertWeather(weather)
        }
    }

}

class NetworkUtils {

    companion object{

        fun isInternetAvailable(context: Context): Boolean {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) ?: false
            }
        }
    }
}

class QuoteRepository(
    private val quoteService: api,
    private val quoteDatabase: DatabaseDb,
    private val applicationContext: Context,
) {

    private val quotesLiveData = MutableLiveData<weather>()

    val quotes: LiveData<weather>
        get() = quotesLiveData

    suspend fun getQuotes(latitude:Double, longitude:Double){

        if(NetworkUtils.isInternetAvailable(applicationContext)){
            val result = quoteService.getWeather(latitude, longitude)
            if(result.body() != null){
                quoteDatabase.weatherDao().insertWeather(
                    WeatherEntity(
                        latitude = result.body()!!.latitude,
                        longitude = result.body()!!.longitude,
                        code = result.body()!!.current_weather.weathercode
                    )
                )
                quotesLiveData.postValue(result.body())
            }
        }
        else{
            val quotes = quoteDatabase.weatherDao().getWeather()
        }

    }
}