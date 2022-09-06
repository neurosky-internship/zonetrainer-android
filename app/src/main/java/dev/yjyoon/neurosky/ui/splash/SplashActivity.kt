package dev.yjyoon.neurosky.ui.splash

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.neurosky.ui.base.BaseActivity
import dev.yjyoon.neurosky.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestBluetoothPermissions()
        
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

            SplashScreen()
        }

        lifecycleScope.launch {
            delay(SPLASH_TIME_MILLIS)
            startMainActivity()
        }
    }

    private fun requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                ), 1
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.BLUETOOTH), 1)
        }
    }

    private fun startMainActivity() {
        MainActivity.startActivity(this)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_MILLIS = 1_500L
    }
}
