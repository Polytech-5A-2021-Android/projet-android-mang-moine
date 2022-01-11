package com.polytech.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.polytech.myapplication.model.User

class PersonalDataViewModel(userParam: User) : ViewModel()
{

    var user = userParam
    init {
        Log.i("PersonalDataViewModel", "created")
    }
    fun onGender(gender: String) {
        user?.gender = gender
    }

}