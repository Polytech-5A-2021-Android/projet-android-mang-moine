package com.polytech.myapplication.model

import androidx.databinding.InverseMethod
import kotlin.properties.Delegates

class Connexion {

    companion object {
        lateinit var utilisateur: Utilisateur
        //lateinit var seuil: Seuil
        lateinit var seuils: Map<Long, Seuil>
        var connex: Boolean = false

        @JvmStatic
        fun getSeuil(seuilId: Long): Seuil? {
            return seuils[seuilId]
        }

    }


}