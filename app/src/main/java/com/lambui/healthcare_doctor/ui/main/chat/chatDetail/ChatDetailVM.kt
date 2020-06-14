package com.lambui.healthcare_doctor.ui.main.chat.chatDetail

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lambui.healthcare_app.data.model.ConversationModel
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.MessageModel
import com.lambui.healthcare_doctor.data.source.repositories.ConversationRepositoryImpl
import com.lambui.healthcare_doctor.data.source.repositories.MessageRepositoryImpl
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatDetailVM(
    private val userLocalRepository: UserLocalRepository,
    private val authRepository: UserAuthRepository
) : BaseViewModel() {
    private lateinit var refMessage: CollectionReference
    private lateinit var refConversation: DocumentReference

    private lateinit var conversationId: String

    private lateinit var repoMessage: MessageRepositoryImpl
    private lateinit var repoConversation: ConversationRepositoryImpl

    private val mDisposable = CompositeDisposable()

    var conversation: MutableLiveData<ConversationModel> = MutableLiveData()
    var conversationUpdate: MutableLiveData<ConversationModel> = MutableLiveData()

    var messages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData()
    var pendingWriteMessageIds: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var failureWriteMessageIds: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())

    var senderId: String = "sender"
    var receiverId: String = "receiver"

    var partnerName: MutableLiveData<String> = MutableLiveData()
    var partnerAvatarUrl: MutableLiveData<String> = MutableLiveData()

    init {

    }

    private fun buildConversationId(id1: String, id2: String): String {
        return if (id1.compareTo(id2) == -1) id2 + '_' + id1
        else id1 + '_' + id2
    }

    fun getCurrentUserId(): String = userLocalRepository.getUserId()!!

    fun initUser(senderId: String, receiverId: String) {
        this.senderId = senderId
        this.receiverId = receiverId
        conversationId = buildConversationId(senderId, receiverId)
        refConversation = FirebaseFirestore.getInstance()
            .collection("conversation")
            .document(conversationId)

        refMessage = refConversation
            .collection("messages")

        repoMessage = MessageRepositoryImpl(refMessage)
        repoConversation = ConversationRepositoryImpl(refConversation)
    }

    private fun loadConversationUpdate(conversationId: String) {
        repoConversation.getConversation2(conversationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ConversationModel> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    mDisposable.add(d)
                }

                override fun onNext(t: ConversationModel) {
                    conversationUpdate.value = t
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    fun loadConversation(senderId: String, receiverId: String) {
        val conversationId = buildConversationId(senderId, receiverId)
        repoConversation.isConversationExist(conversationId)
            .flatMap { exist ->
                if (!exist)
                    authRepository.getPatientInformation(receiverId)
                        .doOnSuccess {
                            partnerName.postValue(it.data?.fullName ?: "Null")
                            partnerAvatarUrl.postValue(it.data?.avatar ?: "Null")
                        }
                        .flatMap { responsePatient ->
                            authRepository.getPatientInformation(senderId)
                                .flatMap { responseDoctor ->
                                    repoConversation.createConversation(
                                        senderId,
                                        receiverId,
                                        conversationId,
                                        memberNames = hashMapOf(
                                            senderId to (responseDoctor.data?.fullName
                                                ?: "Null"),
                                            receiverId to (responsePatient.data?.fullName
                                                ?: "Null")
                                        ),
                                        memberAvatars = hashMapOf(
                                            senderId to (responseDoctor.data?.avatar
                                                ?: "Null"),
                                            receiverId to (responsePatient.data?.avatar
                                                ?: "Null")
                                        )
                                    )
                                }
                        }
                else
                    repoConversation.getConversation(conversationId)
                        .doOnSuccess {
                            partnerName.postValue(it.memberNames[receiverId] ?: "Null")
                            partnerAvatarUrl.postValue(it.memberAvatars[receiverId] ?: "Null")
                        }

            }
            .doOnSuccess {
                loadConversationUpdate(it.id)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ConversationModel> {
                override fun onSubscribe(d: Disposable) {
                    isLoading.value = true
                    mDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    isLoading.value = false
                }

                override fun onSuccess(t: ConversationModel) {
                    isLoading.value = false
                    conversation.value = t
                }
            })
    }

    fun loadMessage() {
        repoMessage.getMessage()
            .doOnSubscribe { mDisposable.add(it) }
            .doOnNext { updateSeenStatus() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MutableList<MessageModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    messages.value = ArrayList()
                }

                override fun onComplete() {

                }

                override fun onNext(t: MutableList<MessageModel>) {
                    messages.value = t
                }
            })
    }

    private fun updateSeenStatus() {
        val conversation = conversationUpdate.value!!
        if (conversation.lastSender != senderId && !conversation.seen) {
            repoConversation.seenConversation()
        }
    }

    fun sendMessage(
        conversationId: String,
        senderId: String,
        content: String
    ): Single<MessageModel> {
        val messageId = refMessage.document().id
        pendingWriteMessageIds.value?.add(messageId)
        pendingWriteMessageIds.postValue(pendingWriteMessageIds.value)
        return repoMessage.sendMessage(conversationId, senderId, content, messageId)
            .doOnSuccess {
                pendingWriteMessageIds.value?.remove(messageId)
                pendingWriteMessageIds.postValue(pendingWriteMessageIds.value)
                failureWriteMessageIds.value?.remove(messageId)
                failureWriteMessageIds.postValue(failureWriteMessageIds.value)
                repoConversation.updateConversationAfterSend(conversationId, it)
            }
            .doOnSubscribe { mDisposable.add(it) }
            .doOnError {
                failureWriteMessageIds.value?.add(messageId)
                failureWriteMessageIds.postValue(failureWriteMessageIds.value)
            }
    }

    override fun onCleared() {
        mDisposable.clear()
        repoMessage.dispose()
        repoConversation.dispose()
        super.onCleared()
    }

    fun initFireStore() {

    }
}