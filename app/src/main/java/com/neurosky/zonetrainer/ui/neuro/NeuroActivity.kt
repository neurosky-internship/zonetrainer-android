package com.neurosky.zonetrainer.ui.neuro

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.hbisoft.hbrecorder.HBRecorder
import com.hbisoft.hbrecorder.HBRecorderListener
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.base.BaseActivity
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.theme.NeuroTheme
import com.neurosky.zonetrainer.ui.util.RecorderUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NeuroActivity : BaseActivity(), HBRecorderListener {

    private val viewModel: NeuroViewModel by viewModels()

    private lateinit var hbRecorder: HBRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBluetoothAdapter()
        initHBRecorder()

        setContent {
            NeuroTheme {
                NeuroScreen(
                    viewModel = viewModel,
                    closeActivity = ::finish,
                    onRetry = ::getBluetoothAdapter,
                    startRecording = ::startScreenRecording,
                    stopRecording = ::stopScreenRecording
                )
            }
        }
    }

    override fun onStop() {
        super.onDestroy()
        viewModel.postLogData()
        viewModel.closeTgStreamReader()
        if (hbRecorder.isBusyRecording) {
            hbRecorder.stopScreenRecording()
        }
    }

    private fun getBluetoothAdapter() {
        val bluetoothManager =
            this@NeuroActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
            viewModel.onBluetoothEnabled(bluetoothAdapter)
        } else {
            viewModel.onBluetoothDisabled()
        }
    }

    private fun initHBRecorder() {
        hbRecorder = HBRecorder(this, this)
        hbRecorder.setOutputPath(RecorderUtil.getOutputPath())
        hbRecorder.fileName = RecorderUtil.getFileName()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                hbRecorder.startScreenRecording(data, resultCode);
            }
        }
    }

    private fun startScreenRecording() {
        val mediaProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val permissionIntent = mediaProjectionManager.createScreenCaptureIntent()
        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE)
    }

    private fun stopScreenRecording() {
        hbRecorder.stopScreenRecording()
    }

    override fun HBRecorderOnStart() {
        viewModel.isRecording = true
    }

    override fun HBRecorderOnComplete() {
        viewModel.isRecording = false
        showToast(
            getString(R.string.video_saved, "${hbRecorder.filePath}/${hbRecorder.fileName}"),
            enableLongLength = true
        )
    }

    override fun HBRecorderOnError(errorCode: Int, reason: String?) {
        viewModel.isRecording = false
    }

    companion object {
        private const val SCREEN_RECORD_REQUEST_CODE = 1000
        private const val KEY_GOOGLE_ACCOUNT = "KEY_GOOGLE_ACCOUNT"

        fun startActivity(context: Context, account: GoogleAccount) {
            val intent = Intent(context, NeuroActivity::class.java)
            intent.putExtra(KEY_GOOGLE_ACCOUNT, account)

            context.startActivity(intent)
        }
    }
}
