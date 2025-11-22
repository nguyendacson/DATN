package com.example.movieseeme.presentation.viewmodels.movie

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val tempFile = File(context.cacheDir, "temp_avatar.jpg")
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }
}