package com.polytech.myapplication.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.SeuilDao
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.model.Seuil
import kotlinx.coroutines.*

class SeuilViewModel(val database: SeuilDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _seuil = MutableLiveData<Seuil>()
    val seuil: LiveData<Seuil>
        get() = _seuil

    init {
        Log.i("SeuilViewModel", "created")
        initializeSeuil()
    }


    private fun initializeSeuil() {
        uiScope.launch {
            val id = Connexion.utilisateur.id
            val s = get(id)

            if(s != null) {
                _seuil.value = s
            } else {
                insert(2000f)
                _seuil.value = get(id)
            }
            Connexion.seuil = _seuil.value!!
        }
    }

    private suspend fun insert(valeur: Float): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            val seuil = Seuil()
            seuil.valeur = valeur
            seuil.utilisateur_id = Connexion.utilisateur.id
            id = database.insert(seuil)
            System.out.println(database.getAllSeuils())
        }
        return id
    }

    private suspend fun get(id: Long) : Seuil? {
        return withContext(Dispatchers.IO) {
            database.getLastSeuilByUtilisateurId(id)
        }
    }

    fun modifier(valeur: Float) {
        uiScope.launch {
            insert(valeur)
            _seuil.value = get(Connexion.utilisateur.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("SeuilViewModel", "destroyed")
    }

}