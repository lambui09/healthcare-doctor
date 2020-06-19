package com.lambui.healthcare_doctor.ui.main.chat

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.lambui.healthcare_app.data.model.ConversationModel
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.source.repositories.ChatRepositoryImpl
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatVM(
  private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {

  private var repoChat: ChatRepositoryImpl = ChatRepositoryImpl(
    FirebaseFirestore.getInstance().collection("conversation")
  )
  var conversationList: MutableLiveData<MutableList<ConversationModel>> =
    MutableLiveData(mutableListOf())

  private val mDisposable = CompositeDisposable()

  fun getCurrentUserId(): String? {
    return userLocalRepository.getUserId()
  }

  fun getConversationList(yourId: String) {
    repoChat.getConversationList(yourId)
      .doOnSubscribe { mDisposable.add(it) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : Observer<MutableList<ConversationModel>> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(t: MutableList<ConversationModel>) {
          conversationList.value = t
        }

        override fun onError(e: Throwable) {
          showError(e.message)
        }

      })
  }

  override fun onCleared() {
    mDisposable.clear()
    repoChat.dispose()
    super.onCleared()
  }
}