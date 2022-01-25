package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.MesureDao
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.model.Mesure
import com.polytech.myapplication.model.Seuil
import com.polytech.myapplication.service.IotApi
import com.polytech.myapplication.service.IotApiService
import kotlinx.coroutines.*

class ListMesuresViewModel(val database: MesureDao,
                           application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _mesures = MutableLiveData<List<Mesure>>()

    val mesures: LiveData<List<Mesure>>
        get() = _mesures

    init {
        Log.i("ListViewModel", "created")
//        initializeMesures()       //Sans API
        getListTaux();

    }

    private fun initializeMesures() {
        uiScope.launch {
            _mesures.value = getMesuresByUtilisateur()
        }
    }

    private fun getListTaux(){
        uiScope.launch {
//            _mesures.value = getMesuresByUtilisateur()
            var getPropertiesDeferred = IotApi.retrofitService.getMesures();
            println("avant try")
            try {
                println("Dans try")
                var listResult = getPropertiesDeferred.await()
                println(listResult)
                _mesures!!.value = listResult       //Bug ici
            } catch (e: Exception) {
                println(e)
                initializeMesures() //Si API injoignable.
            }
        }
    }


    private suspend fun getMesuresByUtilisateur(): List<Mesure>? {
        return withContext(Dispatchers.IO) {
            var mesure1 = Mesure()
            mesure1.quantite_gaz = 500f
            mesure1.date_mesure = System.currentTimeMillis()
            mesure1.seuil_id = Connexion.seuils.keys.last()

            var mesure2 = Mesure()
            mesure2.quantite_gaz = 750.25F
            mesure2.date_mesure = System.currentTimeMillis()
            mesure2.seuil_id = Connexion.seuils.keys.last()

            database.insert(mesure1)
            database.insert(mesure2)

            val mesures = database.getMesuresByUtilisateurId(Connexion.utilisateur.id)
            mesures
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("ListMesuresViewModel", "destroyed")
        viewModelJob.cancel()
    }

}