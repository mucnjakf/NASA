package hr.algebra.nasa.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.nasa.DATA_IMPORTED
import hr.algebra.nasa.HostActivity
import hr.algebra.nasa.framework.setBooleanPreference
import hr.algebra.nasa.framework.startActivity

class NasaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED, true)
        context.startActivity<HostActivity>()
    }
}