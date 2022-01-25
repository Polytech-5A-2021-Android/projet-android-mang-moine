package com.polytech.myapplication.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.polytech.myapplication.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://localhost:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface IotApiService {

    @GET("mesure")
    fun getMesures(): Deferred<List<Mesure>>

    @GET("seuil")
    fun getSeuils(): Deferred<List<Seuil>>

    @GET("activate")
    fun activerVentilateur()

    @GET("utilisateur")
    fun getUtilisateurs(): Deferred<List<Utilisateur>>


}

object IotApi {
    val retrofitService : IotApiService by lazy { retrofit.create(IotApiService::class.java) }

    override fun toString():String {
        return ""
    }

}
