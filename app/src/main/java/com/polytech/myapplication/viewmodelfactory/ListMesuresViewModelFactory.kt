package com.polytech.myapplication.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.database.MesureDao
import com.polytech.myapplication.viewmodel.ListMesuresViewModel

class ListMesuresViewModelFactory(private val dataSource: MesureDao,
                                  private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListMesuresViewModel::class.java)) {
            return ListMesuresViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}