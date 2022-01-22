package com.polytech.myapplication.model

import androidx.databinding.InverseMethod
import kotlin.properties.Delegates

class Connexion {

    companion object {
        lateinit var utilisateur: Utilisateur
        lateinit var seuil: Seuil
        var connex: Boolean = false
    }


}