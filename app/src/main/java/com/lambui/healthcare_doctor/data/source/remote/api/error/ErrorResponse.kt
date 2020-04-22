package com.lambui.healthcare_doctor.data.source.remote.api.error

import android.util.Log
import com.lambui.healthcare_doctor.utils.extension.convertStringToListStringWithFormatPattern
import com.lambui.healthcare_doctor.utils.extension.notNull
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

data class ErrorResponse(private val response: Response<*>?) {
  var code: Int = 0

  var message: String? = null

  var messageList: String? = null

  var allMessage: String? = null

  init {
    response.notNull {
      code = it.code()
      Log.d(TAG, "errorCode: $code")

      try {
        val errorJson = JSONObject(it.errorBody()?.string()).toString()
        val errorMsg = JSONObject(errorJson).getString(ERROR_MESSAGE_PARAM)

        if (errorMsg.isNotBlank()) {
          message = errorMsg
          allMessage = errorMsg
          Log.e(TAG, "errorMsg: $errorMsg")
        }

        val errorObjects = JSONObject(errorJson).getJSONObject(ERRORS_PARAM)
        val errorMsgList = arrayListOf<String>()
        val keys = errorObjects.keys()
        while (keys.hasNext()) {
          val key = keys.next()
          val array = errorObjects.getJSONArray(key)
          for (i in 0 until array.length()) {
            errorMsgList.add(array.getString(i))
            Log.d(TAG, "key :" + key + ", value: " + array.getString(i))
          }
        }

        messageList = errorMsgList.convertStringToListStringWithFormatPattern(
          ENTER_SPACE_FORMAT)
        Log.d(TAG, "messageList: $messageList")


        allMessage = if (allMessage?.isNotBlank() == true) {
          allMessage + "\n" + messageList
        } else {
          messageList
        }

        Log.d(TAG, "allMessage: $allMessage")

      } catch (e: JSONException) {
        Log.e(TAG, e.localizedMessage)
      } catch (e: IOException) {
        Log.e(TAG, e.localizedMessage)
      }
    }
  }

  companion object {
    private const val TAG = "ErrorResponse"
    private const val ENTER_SPACE_FORMAT = "\n"
    private const val ERRORS_PARAM = "error"
    private const val ERROR_MESSAGE_PARAM = "message"
  }

}