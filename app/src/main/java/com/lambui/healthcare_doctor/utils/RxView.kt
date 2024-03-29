package com.lambui.healthcare_doctor.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.constant.Constants.TIME_OUT_EVENT_CLICK
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

object RxView {
    fun clicks(view: View, isCheckNetwork: Boolean): Observable<View> {
        val source: ObservableOnSubscribe<View> = ObservableOnSubscribe { emitter ->
            emitter.setCancellable {
                view.setOnClickListener(null)
                emitter.onComplete()
            }

            view.setOnClickListener {
                val isConnected = InternetManager.isConnected()
                if (isCheckNetwork && !isConnected) {
                    val errorMessage = view.context.getString(R.string.msg_error_no_internet)
                    Toast.makeText(view.context, errorMessage, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                emitter.onNext(view)
            }
        }
        return Observable.create(source).throttleFirst(
            1, TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        )
    }

    fun search(editText: EditText): Observable<String> {
        val subject = PublishSubject.create<String>()

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //No-Op
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                subject.onNext(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                //No-Op
            }
        })
        return subject
    }

    fun clickCheckNetwork(view: View, listener: IListenerCheckNetWork?): Observable<View> {
        val sources: ObservableOnSubscribe<View> = ObservableOnSubscribe { emitter ->
            emitter.setCancellable {
                view.setOnClickListener(null)
                emitter.onComplete()
            }
            view.setOnClickListener {
                val isConnected = InternetManager.isConnected()
                if (!isConnected) {
                    listener?.showError(isConnected)
                    return@setOnClickListener
                }
                emitter.onNext(view)
            }
        }
        return Observable.create(sources)
            .throttleFirst(
                TIME_OUT_EVENT_CLICK,
                TimeUnit.MILLISECONDS,
                AndroidSchedulers.mainThread()
            )
    }

    interface IListenerCheckNetWork {
        fun showError(isCheckNetwork: Boolean)
    }
}