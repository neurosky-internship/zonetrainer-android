package com.neurosky.zonetrainer.ui.neuro

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.component.NeuroDonut
import com.neurosky.zonetrainer.ui.theme.Black
import com.neurosky.zonetrainer.ui.theme.NeuroGreen
import com.neurosky.zonetrainer.ui.theme.NeuroPurple
import com.neurosky.zonetrainer.ui.theme.NeuroRed
import com.neurosky.zonetrainer.ui.theme.White
import com.neurosky.zonetrainer.util.getCameraProvider

@Composable
fun NeuroContent(
    attention: Int,
    meditation: Int,
    onBack: () -> Unit,
    isRecording: Boolean,
    startRecording: () -> Unit,
    stopRecording: () -> Unit
) {
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(isRecording) {
        systemUiController.setStatusBarColor(
            color = if (isRecording) NeuroGreen else White,
            darkIcons = !isRecording
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            FilledIconButton(
                onClick = onBack,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Black,
                    containerColor = White
                ),
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(imageVector = Icons.Rounded.ChevronLeft, contentDescription = null)
            }
            FilledIconButton(
                onClick = {
                    lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                        CameraSelector.LENS_FACING_FRONT
                    } else {
                        CameraSelector.LENS_FACING_BACK
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Black,
                    containerColor = White
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Icon(imageVector = Icons.Rounded.Cameraswitch, contentDescription = null)
            }
            FilledIconButton(
                onClick = if (isRecording) stopRecording else startRecording,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Black,
                    containerColor = White
                ),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                if (isRecording) {
                    Icon(
                        imageVector = Icons.Rounded.Stop,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.FiberManualRecord,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                NeuroDonut(
                    title = stringResource(id = R.string.attention),
                    value = attention.toFloat(),
                    color = NeuroRed,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(18.dp))
                NeuroDonut(
                    title = stringResource(id = R.string.meditation),
                    value = meditation.toFloat(),
                    color = NeuroPurple,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
