package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.UtilisateurDao
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.model.Utilisateur
import com.polytech.myapplication.service.IotApi
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class ConnexionViewModel(val database: UtilisateurDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var listUtilisateurs: List<Utilisateur>? = null

    private val _utilisateur = MutableLiveData<Utilisateur>()
    val utilisateur: LiveData<Utilisateur>
        get() = _utilisateur

    init {
        Log.i("ConnexionViewModel", "created")
        initializeUtilisateur()
    }

    private fun initializeUtilisateur() {
        uiScope.launch {
            _utilisateur.value = Utilisateur()
            listUtilisateurs = getUtilisateurs()
        }


    }

    private suspend fun getUtilisateurs(): List<Utilisateur>? {
        return withContext(Dispatchers.IO) {
            database.getUtilisateurs()
        }
    }

    private suspend fun insert(utilisateur: Utilisateur): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(utilisateur)
        }
        return id
    }

    fun connexion() {
        connect(utilisateur.value!!)
    }

    fun testInscription(): Boolean {
        return utilExists(utilisateur.value!!, true) != null
    }

    fun inscription() {
        uiScope.launch {
            insert(utilisateur.value!!)
        }
    }


    private fun utilExists(utilisateur: Utilisateur, usernameOnly: Boolean): Utilisateur? {
        var found = false
        var utilTrouve:Utilisateur? = null
        for(u in listUtilisateurs!!) {
            if(found)
                break
            val okUsername = utilisateur.username.equals(u.username)
            val okPassword = utilisateur.password.equals(u.password)
            val ok: Boolean = if(usernameOnly) {
                okUsername
            } else {
                okUsername && okPassword
            }

            if(ok) {
                found = true
                utilTrouve = u
            }
        }
        return utilTrouve
    }


    private fun connect(utilisateur: Utilisateur) {
        val utilConnect = utilExists(utilisateur, false)

        if(utilConnect != null) {
            Connexion.utilisateur = utilConnect
            Connexion.connex = true
        } else {
            Connexion.connex = false
        }

    }




    override fun onCleared() {
        super.onCleared()
        Log.i("ConnexionViewModel", "destroyed")
    }

}