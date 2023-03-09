package compose.weather.everyone.mobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun insertWeather(weatherCode: WeatherEntity)

    @Query("select * from Weather")
    fun getWeather(): Flow<List<WeatherEntity>>
}