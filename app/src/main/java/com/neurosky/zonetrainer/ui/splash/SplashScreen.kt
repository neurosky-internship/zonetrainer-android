package com.neurosky.zonetrainer.ui.splash

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.model.toModel
import com.neurosky.zonetrainer.ui.util.GoogleSignInContract
import java.net.UnknownHostException

@Composable
fun SplashScreen(
    isLoginButtonVisible: Boolean,
    login: (GoogleAccount) -> Unit,
    navigateToHome: (GoogleAccount) -> Unit
) {
    val googleSignInActivity =
        rememberLauncherForActivityResult(contract = GoogleSignInContract()) { task ->
            runCatching {
                val account =
                    task?.getResult(ApiException::class.java)?.toModel()
                        ?: throw UnknownHostException()
                login(account)
                navigateToHome(account)
            }.onFailure { Log.e("google auth fail", it.stackTraceToString()) }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(256.dp)
        )
        if (isLoginButtonVisible) {
            ElevatedButton(
                onClick = { googleSignInActivity.launch(1000) },
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                modifier = Modifier.align(Alignment.BottomCenter)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier.width(24.dp)
                    )
                    Text(
                        text = stringResource(R.string.sign_in_with_google),
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.img_brand),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .width(96.dp)
            )
        }
    }
}
