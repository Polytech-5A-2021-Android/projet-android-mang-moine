package com.polytech.myapplication.adapter

import com.polytech.myapplication.model.Mesure

class MesureListener(val clickListener: (mesureid: Long) -> Unit) {
    fun onClick(mesure: Mesure) = clickListener(mesure.id)
}