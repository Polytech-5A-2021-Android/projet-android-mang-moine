package com.polytech.myapplication.database

import androidx.room.*
import com.polytech.myapplication.model.*

@Dao
interface SeuilDao {

    @Insert
    fun insert(seuil: Seuil): Long

    @Delete
    fun delete(seuil: Seuil)

    @Query("SELECT * from seuil WHERE id = :key")
    fun get(key: Long): Seuil?

    @Query("SELECT * FROM seuil WHERE utilisateur_id = :utilId ORDER BY id DESC LIMIT 1")
    fun getLastSeuilByUtilisateurId(utilId: Long): Seuil?

    @Query("SELECT * FROM seuil")
    fun getAllSeuils(): List<Seuil>?

    @Query("SELECT * FROM seuil WHERE utilisateur_id = :utilId")
    fun getAllSeuilsByUtilisateurId(utilId: Long): List<Seuil>?

}