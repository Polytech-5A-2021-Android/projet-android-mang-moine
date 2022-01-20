package com.polytech.myapplication.database

import androidx.room.*
import com.polytech.myapplication.model.User
import com.polytech.myapplication.model.Utilisateur

@Dao
interface UtilisateurDao {

    @Insert
    fun insert(utilisateur: Utilisateur): Long
    @Delete
    fun delete(utilisateur: Utilisateur)
    @Update
    fun update(utilisateur: Utilisateur)
    @Query("SELECT * from utilisateur WHERE id = :key")
    fun get(key: Long): Utilisateur?
    @Query("SELECT * FROM utilisateur ORDER BY id DESC LIMIT 1")
    fun getLastUtilisateur(): Utilisateur?
    @Query("SELECT * FROM utilisateur")
    fun getUtilisateurs(): List<Utilisateur>?

    @Query("SELECT * FROM utilisateur where username = :username AND password = :password")
    fun getUtilisateurByUsernameAndPassword(username: String?, password: String?) : Utilisateur?


}