package dev.yjyoon.neurosky.ui.splash

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.neurosky.MainActivity
import dev.yjyoon.neurosky.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    private fun startMainActivity() {
        MainActivity.startActivity(this)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_MILLIS = 1_500L
    }
}
