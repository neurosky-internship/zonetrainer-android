package com.neurosky.zonetrainer.ui.splash

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.neurosky.zonetrainer.R

@Composable
fun SplashScreen(
    googleSignInClient: GoogleSignInClient,
    isLoginButtonVisible: Boolean,
    login: (String) -> Unit
) {
    val googleSignInActivity =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val account: GoogleSignInAccount =
                        GoogleSignIn.getSignedInAccountFromIntent(intent).result

                    login(account.id!!)
                }
            }
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
                onClick = { googleSignInActivity.launch(googleSignInClient.signInIntent) },
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
