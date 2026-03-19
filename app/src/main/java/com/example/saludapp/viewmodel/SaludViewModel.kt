package com.example.saludapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.saludapp.PerfilDataStore
import kotlinx.coroutines.launch

class SaludViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = PerfilDataStore(application)

    var tienePerfil by mutableStateOf(false)
        private set
    var nombre by mutableStateOf("")
    var edad by mutableStateOf("")
    var estatura by mutableStateOf("")
    var peso by mutableStateOf("")

    init {
        viewModelScope.launch {
            dataStore.perfilFlow.collect { datosGuardados ->
                tienePerfil = datosGuardados.tienePerfil
                nombre = datosGuardados.nombre
                edad = datosGuardados.edad
                estatura = datosGuardados.estatura
                peso = datosGuardados.peso
            }
        }
    }

    fun guardarPerfil() {
        if (nombre.isNotBlank() && edad.isNotBlank() && estatura.isNotBlank() && peso.isNotBlank()) {
            tienePerfil = true

            viewModelScope.launch {
                dataStore.guardarPerfil(true, nombre, edad, estatura, peso)
            }
        }
    }

    fun editarPerfil() {
        tienePerfil = false
        viewModelScope.launch {
            dataStore.guardarPerfil(false, nombre, edad, estatura, peso)
        }
    }

    fun calcularIMC(): Double {
        val pesoKg = peso.toDoubleOrNull() ?: 0.0
        val estaturaCm = estatura.toDoubleOrNull() ?: 0.0
        if (pesoKg <= 0.0 || estaturaCm <= 0.0) return 0.0
        val estaturaMetros = estaturaCm / 100
        return pesoKg / (estaturaMetros * estaturaMetros)
    }

    fun obtenerCategoriaIMC(imc: Double): String {
        return when {
            imc == 0.0 -> "--"
            imc < 18.5 -> "Bajo peso"
            imc in 18.5..24.9 -> "Normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            else -> "Obesidad"
        }
    }

    fun obtenerMensajeIMC(imc: Double): String {
        return when {
            imc == 0.0 -> "Ingresa tus datos para calcular"
            imc < 18.5 -> "¡Cuidado! Tu peso es bajo"
            imc in 18.5..24.9 -> "¡Excelente! Mantén tu estilo de vida"
            imc in 25.0..29.9 -> "Ten cuidado, estás en sobrepeso"
            else -> "Atención, consulta a un médico"
        }
    }

    fun calcularPesoIdeal(): Double {
        val estaturaCm = estatura.toDoubleOrNull() ?: 0.0
        if (estaturaCm <= 0.0) return 0.0
        val estaturaMetros = estaturaCm / 100
        return 22.5 * (estaturaMetros * estaturaMetros)
    }
}
