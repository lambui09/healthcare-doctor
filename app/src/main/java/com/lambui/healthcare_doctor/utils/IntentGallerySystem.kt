package com.lambui.healthcare_doctor.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.constant.Constants.PICK_IMAGE_RESULT
import com.lambui.healthcare_doctor.constant.Constants.TEMPORARY_URI_FILE
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.ArrayList

object IntentGallerySystem {
    /**
     * Select photo from gallery
     */
    fun getPickImageIntent(): Intent {
        val imageIntent =
            Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"))
        imageIntent.type = "image/*"
        return imageIntent
    }

    /**
     * Get the Uri from Bitmap
     */
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver, inImage, "Title",
            null
        )
        return Uri.parse(path)
    }

    /**
     * @param context
     * @return select camera and gallery
     */
    fun getPickImageIntent(context: Context, tempImageFile: File): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra("return-data", true)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile))

        intentList = addIntentsToList(context, intentList, pickIntent)
        intentList = addIntentsToList(context, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                context.resources.getString(R.string.text_choose_application)
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    /**
     * @param context
     * @return select camera and gallery
     */
    fun getPickImageIntent(context: Context, tempImageUri: Uri): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempImageUri)
        takePhotoIntent.addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )

        intentList = addIntentsToList(context, intentList, pickIntent)
        intentList = addIntentsToList(context, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                context.resources.getString(R.string.text_choose_application)
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }


    private fun addIntentsToList(
        context: Context, list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    fun getPickImageResultUri(
        context: Context, data: Intent?,
        child: String = PICK_IMAGE_RESULT
    ): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        return if (isCamera) getCaptureImageOutputUri(context, child) else data!!.data
    }

    private fun getCaptureImageOutputUri(context: Context, child: String): Uri? {
        var outputFileUri: Uri? = null
        val getImage = context.externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, child))
        }
        return outputFileUri
    }

    fun isFromCamera(data: Intent?): Boolean {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        return isCamera
    }

    fun getImageFileFromCache(context: Context, child: String = PICK_IMAGE_RESULT): File? {
        var file: File? = null
        val getImage = context.externalCacheDir
        if (getImage != null) {
            file = File(getImage.path, child)
        }
        return file
    }

    fun getPickImageChooserIntent(context: Context, child: String = PICK_IMAGE_RESULT): Intent {
        var filePathImageCamera: File? = null
        try {
            filePathImageCamera = FileHelper.createImageFile(context)
        } catch (ex: IOException) {
            // Error occurred while creating the File

        }
        val outputFileUri = Uri.fromFile(filePathImageCamera)
        TEMPORARY_URI_FILE = outputFileUri

        val allIntents = ArrayList<Intent>()
        val packageManager = context.packageManager
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            if (res.activityInfo.name != "com.android.documentsui.DocumentsActivity" && res.activityInfo.name != "com.google.android.apps.docs.app.GetContentActivity") {
                val intent = Intent(galleryIntent)
                intent.component =
                    ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                intent.setPackage(res.activityInfo.packageName)
                allIntents.add(intent)
            }
        }
        val mainIntent = allIntents[allIntents.size - 1]
        allIntents.remove(mainIntent)
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }
}