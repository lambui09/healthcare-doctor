package com.lambui.healthcare_doctor.data.source.repositories

import com.google.firebase.firestore.*
import com.lambui.healthcare_doctor.data.model.MessageModel
import io.reactivex.Observable
import io.reactivex.Single

interface MessageRepository {
    fun getMessage(): Observable<MutableList<MessageModel>>
    fun loadMore(
        lastSnapshot: DocumentSnapshot
    ): Single<MutableList<MessageModel>>

    fun sendMessage(
        conversationId: String,
        senderId: String,
        content: String,
        messageId: String
    ): Single<MessageModel>

    fun dispose()
}

class MessageRepositoryImpl(
    private val messageFirestoreRef: CollectionReference
) : MessageRepository {
    private val registrations: MutableList<ListenerRegistration> = mutableListOf()
    override fun getMessage(): Observable<MutableList<MessageModel>> {
        return Observable.create { emitter ->
            val registration = messageFirestoreRef
                .orderBy("createAt", Query.Direction.ASCENDING)
//                .limitToLast(5)
                .addSnapshotListener() { snapshot, e ->
                    when {
                        e != null -> emitter.onError(e)
                        snapshot == null -> emitter.onError(Throwable("null messages"))
                        else -> {
                            emitter.onNext(
                                snapshot.map {
                                    it.toObject(MessageModel::class.java)
                                }.toMutableList()
                            )
//                            for (change in snapshot.documentChanges) {
//                                if (change.type == DocumentChange.Type.ADDED) {
//
//                                }
//                            }
                        }
                    }
                }
            registrations.add(registration)
        }
    }

    override fun loadMore(
        lastSnapshot: DocumentSnapshot
    ): Single<MutableList<MessageModel>> {
        return Single.create {
            messageFirestoreRef
                .orderBy("createAt", Query.Direction.DESCENDING)
                .startAfter(lastSnapshot)
                .limit(5)
        }
    }

    override fun sendMessage(
        conversationId: String,
        senderId: String,
        content: String,
        messageId: String
    ): Single<MessageModel> {
        return Single.create { emitter ->
            run {
                val message = MessageModel(
                    conversationId = conversationId,
                    sender = senderId,
                    content = content,
                    id = messageId
                )
                messageFirestoreRef
                    .document(messageId).set(message)
                    .addOnSuccessListener {
                        emitter.onSuccess(message)
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
            }
        }
    }

    override fun dispose() {
        registrations.forEach {
            it.remove()
        }
    }
}

