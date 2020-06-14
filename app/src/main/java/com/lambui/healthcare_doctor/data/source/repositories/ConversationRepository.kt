package com.lambui.healthcare_doctor.data.source.repositories

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ListenerRegistration
import com.lambui.healthcare_app.data.model.ConversationModel
import com.lambui.healthcare_doctor.data.model.MessageModel
import io.reactivex.Observable
import io.reactivex.Single

interface ConversationRepository {
    fun isConversationExist(id: String): Single<Boolean>
    fun getConversation(id: String): Single<ConversationModel>
    fun getConversation2(id: String): Observable<ConversationModel>
    fun createConversation(
        user1: String,
        user2: String,
        conversationId: String,
        memberNames: HashMap<String, String>,
        memberAvatas: HashMap<String, String>
    ): Single<ConversationModel>

    fun updateConversationAfterSend(conversationId: String, lastMesasge: MessageModel)
    fun seenConversation()

    fun dispose()
}

class ConversationRepositoryImpl(
    private val conversationFirestoreRef: DocumentReference
) : ConversationRepository {
    private val registrations: MutableList<ListenerRegistration> = mutableListOf()

    override fun isConversationExist(id: String): Single<Boolean> {
        return Single.create { emiter ->
            conversationFirestoreRef
                .get()
                .addOnSuccessListener { snapshot ->
                    emiter.onSuccess(snapshot?.data != null)
                }
                .addOnFailureListener { e -> emiter.onError(e) }
        }
    }

    override fun getConversation(id: String): Single<ConversationModel> {
        return Single.create { emitter ->
            conversationFirestoreRef
                .get().addOnSuccessListener { documentSnapshot ->
                    val conversation: ConversationModel? =
                        documentSnapshot.toObject(ConversationModel::class.java)
                    if (conversation == null)
                        emitter.onError(java.lang.Exception("null conversation"))
                    else
                        emitter.onSuccess(conversation)
                }
                .addOnFailureListener { e -> emitter.onError(e) }
        }
    }

    override fun getConversation2(id: String): Observable<ConversationModel> {
        return Observable.create { emitter ->
            val registration = conversationFirestoreRef
                .addSnapshotListener { snapshot, e ->
                    when {
                        e != null -> emitter.onError(e)
                        snapshot == null -> emitter.onError(Throwable("null conversation"))
                        else -> {
                            emitter.onNext(
                                snapshot.toObject(ConversationModel::class.java) as ConversationModel
                            )
                        }
                    }
                }
            registrations.add(registration)
        }
    }

    override fun createConversation(
        user1: String,
        user2: String,
        conversationId: String,
        memberNames: HashMap<String, String>,
        memberAvatars: HashMap<String, String>
    ): Single<ConversationModel> {
        return Single.create { emmiter ->
            run {
                val conversation = ConversationModel(
                    id = conversationId,
                    members = mutableListOf(user1, user2),
                    memberNames = memberNames,
                    memberAvatars = memberAvatars
                )
                conversationFirestoreRef
                    .set(conversation)
                    .addOnSuccessListener {
                        emmiter.onSuccess(conversation)
                    }
                    .addOnFailureListener {
                        emmiter.onError(it)
                    }
            }
        }
    }

    override fun updateConversationAfterSend(conversationId: String, lastMesasge: MessageModel) {
        conversationFirestoreRef
            .update(
                "updateAt", lastMesasge.createAt,
                "lastMessage", lastMesasge.content,
                "seen", false,
                "lastSender", lastMesasge.sender
            )
    }

    override fun seenConversation() {
        conversationFirestoreRef
            .update(
                "seen", true
            )
    }

    override fun dispose() {

    }

}
