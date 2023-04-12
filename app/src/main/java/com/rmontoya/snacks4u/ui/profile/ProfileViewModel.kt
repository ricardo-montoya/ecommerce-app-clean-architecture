package com.rmontoya.snacks4u.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_rmontoya_snacks4u_ui_search_SearchFragment_GeneratedInjector
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    init {
        _user.postValue(firebaseAuth.currentUser)
    }

    fun signOut() {
        firebaseAuth.signOut()
        reloadUser()
    }

    fun reloadUser() {
        _user.postValue(firebaseAuth.currentUser)
    }
}