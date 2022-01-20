package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.UtilisateurDao
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.model.Utilisateur
import kotlinx.coroutines.*

class ConnexionViewModel(val database: UtilisateurDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var connected = false
    private val _utilisateur = MutableLiveData<Utilisateur>()
    val utilisateur: LiveData<Utilisateur>
        get() = _utilisateur

    init {
        Log.i("IdentityViewModel", "created")
        initializeUtilisateur()
    }

    private fun initializeUtilisateur() {
        uiScope.launch {
            //_utilisateur.value = getUserFromDatabase()
            _utilisateur.value = Utilisateur()
        }
    }


    private suspend fun insert(utilisateur: Utilisateur): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(utilisateur)
        }
        return id
    }

    fun onValidate(num: Number) {
        uiScope.launch {
            val utilisateur = utilisateur.value ?: return@launch
            if(num == 1) {
                insert(utilisateur)
            } else if(num == 2) {
                connect(utilisateur)
            } else {
                deconnect(utilisateur)
            }

        }
    }

    fun isConnected(): Boolean {
        return connected
    }

    private suspend fun connect(utilisateur: Utilisateur) {
        withContext(Dispatchers.IO) {
            val ut = database.getUtilisateurByUsernameAndPassword(utilisateur.username, utilisateur.password)
            System.out.println(ut.toString())
            if(ut != null) {
                Connexion.utilisateur = ut
                Connexion.connex = true
            } else {
                Connexion.connex = false
            }

        }
    }

    private suspend fun deconnect(utilisateur: Utilisateur) {
        withContext(Dispatchers.IO) {

            connected = false

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