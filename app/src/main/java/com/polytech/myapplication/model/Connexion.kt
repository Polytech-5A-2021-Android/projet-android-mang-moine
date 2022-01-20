package com.polytech.myapplication.model

import androidx.databinding.InverseMethod
import kotlin.properties.Delegates

class Connexion {

    companion object {
        lateinit var utilisateur: Utilisateur
        var connex: Boolean = false
    }

    fun setUtilisateur(util: Utilisateur) {
        Companion.utilisateur = util
    }

    fun getUtilisateur(): Utilisateur {
        return Companion.utilisateur
    }

}