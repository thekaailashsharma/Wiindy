package compose.weather.everyone.mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WeatherEntity::class], version = 1)
abstract class DatabaseDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var Instance: DatabaseDb ? = null

        fun getInstance(context: Context): DatabaseDb {
            synchronized(this) {
                if (Instance == null) {
                    Instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            DatabaseDb::class.java,
                            "historyDatabase"
                        )
                            .build()
                }
            }
            return Instance!!
        }
    }
}
