package com.neurosky.zonetrainer.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import com.neurosky.zonetrainer.ui.base.BaseActivity
import com.neurosky.zonetrainer.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val permissionsRequestActivityLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                startMainActivity()
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

            SplashScreen()
        }

        lifecycleScope.launch {
            delay(SPLASH_TIME_MILLIS)
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissions = BLUETOOTH_PERMISSIONS + CAMERA_PERMISSION
            if (!hasPermissions(permissions)) {
                permissionsRequestActivityLauncher.launch(permissions.toTypedArray())
            } else {
                startMainActivity()
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    CAMERA_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                permissionsRequestActivityLauncher.launch(arrayOf(CAMERA_PERMISSION))
            } else {
                startMainActivity()
            }
        }
    }

    private fun hasPermissions(permissions: List<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startMainActivity() {
        MainActivity.startActivity(this)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_MILLIS = 1_500L

        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

        private val BLUETOOTH_PERMISSIONS = listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADVERTISE
        )
    }
}
