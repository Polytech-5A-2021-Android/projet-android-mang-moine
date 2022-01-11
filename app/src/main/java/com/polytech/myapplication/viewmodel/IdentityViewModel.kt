package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.myapplication.database.UserDao
import com.polytech.myapplication.model.User
import kotlinx.coroutines.*

class IdentityViewModel(val database: UserDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        Log.i("IdentityViewModel", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDatabase()
        }
    }
    private suspend fun getUserFromDatabase(): User? {
        return withContext(Dispatchers.IO) {
            var user = database.getLastUser()
            if (user == null) {
                user = User()
                user.id = insert(user)
            }
            user
        }
    }

    private suspend fun insert(user: User): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(user)
        }
        return id
    }

    fun onValidate() {
        uiScope.launch {
            val user = user.value ?: return@launch
            update(user)
        }
    }
    private suspend fun update(user: User) {
        withContext(Dispatchers.IO) {
            database.update(user)
        }
    }
    private suspend fun get(id: Long) {
        withContext(Dispatchers.IO) {
            database.get(id)
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("IdentityViewModel", "destroyed")
    }

    fun onGender(gender: String) {
        _user.value?.gender = gender
    }


}