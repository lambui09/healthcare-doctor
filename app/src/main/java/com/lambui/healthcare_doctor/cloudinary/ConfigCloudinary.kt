package com.lambui.healthcare_doctor.cloudinary

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

object ConfigCloudinary {
    var image = ""
    fun uploadImage(path: Uri) {
        MediaManager.get().upload(path).unsigned("nstxt8xy")
            .option("resource_type", "image")
            .callback(object : UploadCallback {
                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    with(resultData){
                        if (this != null){
                            image = this["url"].toString()
                        }
                    }
                    Log.d("@@@@@@", "upload success" + "${resultData.toString()}" +"${image}")
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.d("@@@@@", "onReschedule")
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.d("@@@@@@", "upload fail" + "${error.toString()}")
                }

                override fun onStart(requestId: String?) {

                }
            }).dispatch()
    }
    fun getUrl() : String{
        return image
    }
}