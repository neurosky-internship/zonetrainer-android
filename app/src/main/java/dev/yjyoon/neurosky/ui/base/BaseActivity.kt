package dev.yjyoon.neurosky.ui.base

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    protected inline val TAG: String get () = this::class.java.simpleName

    protected open fun handleException(throwable: Throwable) {
        showToast(throwable.message ?: "Unknown error")
        Log.e(TAG, throwable.stackTrace.toString())
        finish()
    }

    protected open fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
