package com.neurosky.zonetrainer.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


object RecorderUtil {

    private const val DIRECTORY_NAME = "ZoneTrainer"

    fun getOutputUri(context: Context, fileName: String): Uri {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.RELATIVE_PATH, "DCIM/$DIRECTORY_NAME")
            put(MediaStore.Video.Media.TITLE, fileName)
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        }
        return resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    fun getFileName(prefix: String? = null): String {
        val defaultInfix = "zone_trainer"
        val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        return (prefix ?: "") + defaultInfix + formatter.format(date).replace(" ", "")
    }

    fun getOutputPath(): String {
        createOutputPath()
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath + "/$DIRECTORY_NAME"
    }

    private fun createOutputPath() {
        val path = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            DIRECTORY_NAME
        )
        if (!path.exists()) {
            path.mkdirs()
        }
    }
}
