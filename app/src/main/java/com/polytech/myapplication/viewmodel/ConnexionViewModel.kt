package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.UserDao
import com.polytech.myapplication.database.UtilisateurDao
import com.polytech.myapplication.model.User
import com.polytech.myapplication.model.Utilisateur
import kotlinx.coroutines.*

class ConnexionViewModel(val database: UtilisateurDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _utilisateur = MutableLiveData<Utilisateur>()
    val utilisateur: LiveData<Utilisateur>
        get() = _utilisateur

    init {
        Log.i("IdentityViewModel", "created")
        initializeUtilisateur()
    }

    private fun initializeUtilisateur() {
        uiScope.launch {
            _utilisateur.value = getUserFromDatabase()
        }
    }
    private suspend fun getUserFromDatabase(): Utilisateur? {
        return withContext(Dispatchers.IO) {
            var utilisateur = database.getLastUtilisateur()
            if (utilisateur == null) {
                utilisateur = Utilisateur()
                utilisateur.id = insert(utilisateur)
            }
            utilisateur
        }
    }

    private suspend fun insert(utilisateur: Utilisateur): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(utilisateur)
        }
        return id
    }

    fun onValidate() {
        uiScope.launch {
            val utilisateur = utilisateur.value ?: return@launch
            update(utilisateur)
        }
    }
    private suspend fun update(utilisateur: Utilisateur) {
        withContext(Dispatchers.IO) {
            database.update(utilisateur)
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

}