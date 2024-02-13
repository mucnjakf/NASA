package hr.algebra.nasa.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.nasa.api.NasaFetcher
import hr.algebra.nasa.framework.sendBroadcast

private const val JOB_ID = 1;

class NasaService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        NasaFetcher(this).fetchItems()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NasaService::class.java, JOB_ID, intent)
        }
    }
}