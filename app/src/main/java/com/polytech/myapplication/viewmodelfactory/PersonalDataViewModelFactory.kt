package com.polytech.myapplication.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.myapplication.model.User
import com.polytech.myapplication.viewmodel.PersonalDataViewModel

class PersonalDataViewModelFactory(private val user: User) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalDataViewModel::class.java)) {
            return PersonalDataViewModel(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }


}