package com.polytech.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.polytech.myapplication.model.Mesure
import com.polytech.myapplication.model.Seuil
import com.polytech.myapplication.model.Utilisateur

@Database(entities = [Utilisateur::class, Seuil::class, Mesure::class], version = 5, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract val utilisateurDao: UtilisateurDao
    abstract val seuilDao: SeuilDao
    abstract val mesureDao: MesureDao

    companion object {
        @Volatile
        private var INSTANCE: com.polytech.myapplication.database.Database? = null
        fun getInstance(context: Context): com.polytech.myapplication.database.Database {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.polytech.myapplication.database.Database::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}