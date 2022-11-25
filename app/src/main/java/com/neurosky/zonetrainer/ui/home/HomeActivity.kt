package com.neurosky.zonetrainer.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.neurosky.zonetrainer.ui.base.BaseActivity
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.neuro.NeuroActivity
import com.neurosky.zonetrainer.ui.theme.NeuroBlueGrey
import com.neurosky.zonetrainer.ui.theme.NeuroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NeuroTheme(systemBarsColor = NeuroBlueGrey) {
                HomeScreen(
                    viewModel = viewModel,
                    navigateToNeuro = ::startNeuroActivity
                )
            }
        }
    }

    private fun startNeuroActivity() {
        NeuroActivity.startActivity(this)
    }

    companion object {
        private const val KEY_GOOGLE_ACCOUNT = "KEY_GOOGLE_ACCOUNT"

        fun startActivity(context: Context, account: GoogleAccount) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(KEY_GOOGLE_ACCOUNT, account)

            context.startActivity(intent)
        }
    }
}
