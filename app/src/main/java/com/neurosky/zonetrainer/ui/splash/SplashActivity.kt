package com.neurosky.zonetrainer.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.neurosky.zonetrainer.ui.base.BaseActivity
import com.neurosky.zonetrainer.ui.home.HomeActivity
import com.neurosky.zonetrainer.ui.theme.NeuroTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val permissionsRequestActivityLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                startHomeActivity()
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NeuroTheme {
                SplashScreen()
            }
        }

        lifecycleScope.launch {
            delay(SPLASH_TIME_MILLIS)
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissions = BLUETOOTH_PERMISSIONS + OTHER_PERMISSIONS
            if (!hasPermissions(permissions)) {
                permissionsRequestActivityLauncher.launch(permissions.toTypedArray())
            } else {
                startHomeActivity()
            }
        } else {
            val permissions = OTHER_PERMISSIONS
            if (!hasPermissions(permissions)) {
                permissionsRequestActivityLauncher.launch(permissions.toTypedArray())
            } else {
                startHomeActivity()
            }
        }
    }

    private fun hasPermissions(permissions: List<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startHomeActivity() {
        HomeActivity.startActivity(this)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_MILLIS = 1_500L

        private val OTHER_PERMISSIONS = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        private val BLUETOOTH_PERMISSIONS = listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADVERTISE
        )
    }
}
