package hr.algebra.nasa

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import hr.algebra.nasa.framework.applyAnimation
import hr.algebra.nasa.framework.getBooleanPreference
import hr.algebra.nasa.framework.isOnline
import hr.algebra.nasa.framework.startActivity
import hr.algebra.nasa.service.NasaService
import kotlinx.android.synthetic.main.activity_splash_screen.*

private const val DELAY: Long = 5000
const val DATA_IMPORTED = "hr.algebra.nasa.data_imported"

@Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private var currentApiVersion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        currentApiVersion = Build.VERSION.SDK_INT
        val flags: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags
            val decorView: View = window.decorView
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN === 0) {
                    decorView.systemUiVisibility = flags
                }
            }
        }

        startAnimations()
        redirect()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    private fun startAnimations() {
        tvLoading.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            Handler(Looper.getMainLooper()).postDelayed(
                { startActivity<HostActivity>() },
                DELAY
            )
        } else {
            if (isOnline()) {
                Intent(this, NasaService::class.java).apply {
                    NasaService.enqueueWork(this@SplashScreenActivity, this)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.require_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

    }
}