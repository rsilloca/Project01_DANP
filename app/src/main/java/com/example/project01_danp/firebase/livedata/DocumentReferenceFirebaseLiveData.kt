package com.example.project01_danp.firebase.livedata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.project01_danp.firebase.models.FirebaseEntity
import com.google.firebase.firestore.*
import com.google.firebase.firestore.DocumentReference

class DocumentReferenceFirebaseLiveData<T : FirebaseEntity>(
    private var documentReference: DocumentReference,
    private var entityClass: Class<T>
) : MutableLiveData<T>(), EventListener<DocumentSnapshot> {

    private val TAG: String = "SingleDocumentReference"
    private lateinit var entity: T
    private var listenerRegistration: ListenerRegistration = ListenerRegistration {}
    override fun onActive() {
        listenerRegistration = documentReference.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        listenerRegistration.remove()
        super.onInactive()
    }

    override fun onEvent(document: DocumentSnapshot?, error: FirebaseFirestoreException?) {
        if (document != null && document.exists()) {
            entity = document.toObject(entityClass)!!
            entity.documentId = document.id
            this.setValue(entity)
        } else if (error != null) {
            Log.e(TAG, error.message, error.cause)
        }
    }
}