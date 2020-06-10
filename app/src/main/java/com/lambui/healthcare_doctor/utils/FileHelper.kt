package com.lambui.healthcare_doctor.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.lambui.healthcare_doctor.constant.Constants.TEMPORARY_PATH_FILE
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileHelper {
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).run { format(Date()) }
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        // Create the storage directory if it does not exist
        if (!storageDir?.exists()!! && !storageDir.mkdirs()) {
            Log.d("error", "failed to create directory")
        }

        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        TEMPORARY_PATH_FILE = image.absolutePath
        return image
    }

}