package compose.weather.everyone.mobile.location

import android.location.Location

interface LocationTracker{
    suspend fun getCurrentLocation(): Location?
}

