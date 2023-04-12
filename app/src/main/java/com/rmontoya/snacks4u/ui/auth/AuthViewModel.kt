package com.rmontoya.snacks4u.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    private val _exception = MutableLiveData<String?>()
    val exception: LiveData<String?>
        get() = _exception
    init {
        _exception.value = null
    }

    fun registerUserWithEmail(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.postValue(firebaseAuth.currentUser)
                } else {
                    _exception.postValue(task.exception?.localizedMessage)
                }
            }
    }

    fun loginUserWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.postValue(firebaseAuth.currentUser)
                } else {
                    _exception.postValue(task.exception?.localizedMessage)
                }
            }
    }

    fun errorActionPerformed() {
        this._exception.postValue(null)
    }
}