package com.neurosky.zonetrainer.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    var isLoginButtonVisible by mutableStateOf(false)

    fun login(account: GoogleSignInAccount) {
        Log.d("google login", "token: ${account.idToken}\nid: ${account.id}")
    }
}