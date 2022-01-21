package com.polytech.myapplication.database

import androidx.room.*
import com.polytech.myapplication.model.Mesure

@Dao
interface MesureDao {

    @Insert
    fun insert(mesure: Mesure): Long

    @Delete
    fun delete(mesure: Mesure)

    @Update
    fun update(mesure: Mesure)

    @Query("SELECT * from mesure WHERE id = :key")
    fun get(key: Long): Mesure?

    @Query("SELECT * FROM mesure")
    fun getMesures(): List<Mesure>?

    @Query("SELECT * from mesure WHERE seuil_id = :seuilId")
    fun getMesuresBySeuilId(seuilId: Long): List<Mesure>?

    @Query("SELECT m.* from mesure as m, seuil as s WHERE s.id = m.seuil_id AND s.utilisateur_id = :utilisateurId")
    fun getMesuresByUtilisateurId(utilisateurId: Long): List<Mesure>?


}