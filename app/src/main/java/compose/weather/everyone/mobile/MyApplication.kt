package compose.weather.everyone.mobile

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import compose.weather.everyone.mobile.database.DatabaseDb
import compose.weather.everyone.mobile.database.QuoteRepository
import compose.weather.everyone.mobile.retrofit.NetworkModule
import compose.weather.everyone.mobile.retrofit.api
import compose.weather.everyone.mobile.worker.QuoteWorker
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApplication : Application(){
    lateinit var databaseRepo: QuoteRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }

    private fun setupWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(
            NetworkType.CONNECTED
        ).build()
        val workerRequest = PeriodicWorkRequest.Builder(
            QuoteWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraint).build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize(){
        val service = NetworkModule.provideRetrofitInstance(
            OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build(),
            GsonConverterFactory.create()
        ).create(api::class.java)
        val database = DatabaseDb.getInstance(applicationContext)
        databaseRepo = QuoteRepository(
            service, database, applicationContext
        )
    }
}