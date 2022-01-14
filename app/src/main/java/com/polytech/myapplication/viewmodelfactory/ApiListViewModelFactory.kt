package com.polytech.myapplication.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.viewmodel.ApiListViewModel

class ApiListViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiListViewModel::class.java)) {
            return ApiListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}