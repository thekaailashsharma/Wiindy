package compose.weather.everyone.mobile.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import compose.weather.everyone.mobile.MyApplication
import compose.weather.everyone.mobile.database.QuoteRepository
import compose.weather.everyone.mobile.location.LocationTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class QuoteWorker(
    private val context:Context,
    workerParams: WorkerParameters,

): Worker(context, workerParams) {
    abstract val locationTracker: LocationTracker
    override fun doWork(): Result {

         val repository: QuoteRepository = (context as MyApplication).databaseRepo
        CoroutineScope(Dispatchers.IO).launch{
            repository.getQuotes(
                locationTracker.getCurrentLocation()!!.latitude,
                locationTracker.getCurrentLocation()!!.longitude
            )
        }

        return Result.success()
    }
}