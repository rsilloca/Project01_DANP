package com.example.project01_danp.viewmodel.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.project01_danp.firebase.models.User
import com.example.project01_danp.firebase.repository.UserRepository
import com.example.project01_danp.firebase.service.AuthService

class UserViewModelFirebase: ViewModel(){

    private var currentUser = AuthService.firebaseGetCurrentUser()
    private var userRepository = UserRepository
    private var liveData: DocumentReferenceFirebaseLiveData<User>? = null

    fun getCurrentUserData():DocumentReferenceFirebaseLiveData<User>?{
        Log.e("TAG", currentUser!!.uid)
        if (liveData == null) {
            liveData = currentUser?.let { userRepository.findById(it.uid) }
        }
        return liveData
    }


    fun updateUser(user: User){
        currentUser?.updateEmail(user.email)
        return userRepository.update(user)
    }

}