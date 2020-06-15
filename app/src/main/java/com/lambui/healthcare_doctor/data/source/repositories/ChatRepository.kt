package com.lambui.healthcare_doctor.data.source.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.lambui.healthcare_app.data.model.ConversationModel
import io.reactivex.Observable

interface ChatRepository {
    fun getConversationList(yourId: String): Observable<MutableList<ConversationModel>>

    fun dispose()
}

class ChatRepositoryImpl(
    private val rootConversationRef: CollectionReference
) : ChatRepository {
    private val registrations: MutableList<ListenerRegistration> = mutableListOf()
    override fun getConversationList(yourId: String): Observable<MutableList<ConversationModel>> {
        return Observable.create { emitter ->
            val registration = rootConversationRef
                .orderBy("updateAt", Query.Direction.DESCENDING)
                .whereArrayContains("members", yourId)
                .addSnapshotListener { snapshot, e ->
                    when {
                        e != null -> emitter.onError(e)
                        snapshot == null -> emitter.onError(Throwable("null messages"))
                        else -> {
                            emitter.onNext(
                                snapshot.map {
                                    it.toObject(ConversationModel::class.java)
                                }.toMutableList()
                            )
                            for (change in snapshot.documentChanges) {
                                if (change.type == DocumentChange.Type.ADDED) {

                                }
                            }
                        }
                    }
                }
            registrations.add(registration)
        }
    }

    override fun dispose() {
        registrations.forEach {
            it.remove()
        }
    }
}
