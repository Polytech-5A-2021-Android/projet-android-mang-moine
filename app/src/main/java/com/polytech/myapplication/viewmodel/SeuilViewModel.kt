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
import com.polytech.myapplication.service.IotApi
import com.polytech.myapplication.service.MyApi
import kotlinx.coroutines.*

class SeuilViewModel(val database: SeuilDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _seuil = MutableLiveData<Seuil>()
    val seuil: LiveData<Seuil>
        get() = _seuil

    init {
        Log.i("SeuilViewModel", "created")
//        initializeSeuil()         //Sans api
        getSeuils()                 //Avec api
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
            getMapSeuils(id)
        }
    }

    private fun getSeuils() {
        uiScope.launch {
            var getPropertiesDeferred = IotApi.retrofitService.getSeuils()
            try {
                var listResult = getPropertiesDeferred.await()
                _seuil.value = Seuil()
                _seuil.value!!.valeur = listResult.toFloat()
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun setSeuil(newSeuil: Float){
        uiScope.launch {
            var id = insert(newSeuil)
            var monSeuil = database.get(id)
            var setPropertiesDeferred = IotApi.retrofitService.setSeuil(monSeuil!!)
            try {
                var listResult = setPropertiesDeferred.await()
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun desactivateVenti(action: Boolean){
        uiScope.launch {
            var setPropertiesDeferred = IotApi.retrofitService.desactiverVentilateur(action!!)
            try {
                setPropertiesDeferred.await()
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private suspend fun getMapSeuils(utilId: Long) {
        withContext(Dispatchers.IO) {
            val seuils = database.getAllSeuilsByUtilisateurId(utilId)
            val map = HashMap<Long, Seuil>()
            for(seuil in seuils!!) {
                map[seuil.id] = seuil
            }
            Connexion.seuils = map
        }
    }


    private suspend fun insert(valeur: Float): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            val seuil = Seuil()
            seuil.valeur = valeur
            seuil.utilisateur_id = Connexion.utilisateur.id
            id = database.insert(seuil)
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
            getMapSeuils(Connexion.utilisateur.id)
        }
    }

    override fun onCleared() {
        _seuil.value=null
        super.onCleared()
        Log.i("SeuilViewModel", "destroyed")
    }

}