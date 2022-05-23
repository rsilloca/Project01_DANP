package com.example.proyecto_01_danp.firebase.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.proyecto_01_danp.firebase.models.FirebaseEntity
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import java.util.ArrayList

class MultipleDocumentReferenceLiveData<T : FirebaseEntity?, L : Query?>(// Firebase Utils.
    private val multipleDocuments: L, private val entityClass: Class<T>
) :
    LiveData<List<T>?>(),
    EventListener<QuerySnapshot?> {
    protected var listenerRegistration = ListenerRegistration {}

    // Entity Utils.
    protected val entityList: MutableList<T> = ArrayList()
    override fun onActive() {
        listenerRegistration = multipleDocuments!!.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        listenerRegistration.remove()
        super.onInactive()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (querySnapshot != null && !querySnapshot.isEmpty) {
            Log.e(TAG, "Updating")
            entityList.clear()
            entityList.addAll(querySnapshot.toObjects(entityClass))
            for (i in 0 until querySnapshot.size()) {
                entityList[i]!!.documentId = querySnapshot.documents[i].id
            }
            this.setValue(entityList)
        } else if (error != null) {
            Log.e(TAG, error.message, error.cause)
        }
    }

    companion object {
        protected var TAG = MultipleDocumentReferenceLiveData::class.java.simpleName
    }
}
