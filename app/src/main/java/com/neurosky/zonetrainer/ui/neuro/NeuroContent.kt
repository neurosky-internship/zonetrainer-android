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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.component.NeuroDonut
import com.neurosky.zonetrainer.ui.theme.NeuroPurple
import com.neurosky.zonetrainer.ui.theme.NeuroRed
import com.neurosky.zonetrainer.util.getCameraProvider

@Composable
fun NeuroContent(
    attention: Int,
    meditation: Int
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
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


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(18.dp)
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
