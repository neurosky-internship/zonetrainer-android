package com.neurosky.zonetrainer.ui.base

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    protected inline val TAG: String get() = this::class.java.simpleName

    protected open fun handleException(throwable: Throwable) {
        showToast(throwable.message ?: "Unknown error")
        Log.e(TAG, throwable.stackTraceToString())
        finish()
    }

    protected open fun showToast(text: String, enableLongLength: Boolean = false) {
        Toast.makeText(
            this,
            text,
            if (enableLongLength) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }
}
