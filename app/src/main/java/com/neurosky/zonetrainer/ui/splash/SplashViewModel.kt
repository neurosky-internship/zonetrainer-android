package com.neurosky.zonetrainer.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    var isLoginButtonVisible by mutableStateOf(false)

    fun login(account: GoogleAccount) {
        Log.d("google login", "token: ${account.token}\nid: ${account.id}")
    }
}