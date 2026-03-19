package com.example.saludapp

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "perfil_prefs")

data class PerfilData(
    val tienePerfil: Boolean = false,
    val nombre: String = "",
    val edad: String = "",
    val estatura: String = "",
    val peso: String = ""
)

class PerfilDataStore(context: Context) {
    private val dataStore = context.dataStore
    companion object {
        val TIENE_PERFIL = booleanPreferencesKey("tiene_perfil")
        val NOMBRE = stringPreferencesKey("nombre")
        val EDAD = stringPreferencesKey("edad")
        val ESTATURA = stringPreferencesKey("estatura")
        val PESO = stringPreferencesKey("peso")
    }

    val perfilFlow: Flow<PerfilData> = dataStore.data.map { prefs ->
        PerfilData(
            tienePerfil = prefs[TIENE_PERFIL] ?: false,
            nombre = prefs[NOMBRE] ?: "",
            edad = prefs[EDAD] ?: "",
            estatura = prefs[ESTATURA] ?: "",
            peso = prefs[PESO] ?: ""
        )
    }

    suspend fun guardarPerfil(
        tienePerfil: Boolean, nombre: String, edad: String, estatura: String, peso: String
    ) {
        dataStore.edit { prefs ->
            prefs[TIENE_PERFIL] = tienePerfil
            prefs[NOMBRE] = nombre
            prefs[EDAD] = edad
            prefs[ESTATURA] = estatura
            prefs[PESO] = peso
        }
    }
}