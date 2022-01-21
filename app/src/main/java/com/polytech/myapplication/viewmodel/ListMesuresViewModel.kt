package com.polytech.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.myapplication.database.MesureDao
import com.polytech.myapplication.model.Connexion
import com.polytech.myapplication.model.Mesure
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
        initializeMesures()
    }

    private fun initializeMesures() {
        uiScope.launch {
            _mesures.value = getMesuresByUtilisateur()
        }
    }


    private suspend fun getMesuresByUtilisateur(): List<Mesure>? {
        return withContext(Dispatchers.IO) {
            var mesure1 = Mesure()
            mesure1.quantite_gaz = 500f
            mesure1.date_mesure = System.currentTimeMillis()
            mesure1.seuil_id = 1
            var mesure2 = Mesure()
            mesure2.quantite_gaz = 300f
            mesure2.date_mesure = System.currentTimeMillis()
            mesure2.seuil_id = 1

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