package com.example.saludapp.pagina2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.saludapp.viewmodel.SaludViewModel

@Composable
fun ConsejosScreen(viewModel: SaludViewModel, modifier: Modifier = Modifier) {
    // Obtenemos los datos dinámicos del ViewModel
    val imc = viewModel.calcularIMC()
    val categoria = viewModel.obtenerCategoriaIMC(imc)
    val nombre = viewModel.nombre

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            // Agregamos scroll por si la pantalla es pequeña
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Consejos para ti, $nombre",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu estado actual: $categoria",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mostramos consejos dependiendo de la categoría del usuario
        when (categoria) {
            "Bajo peso" -> {
                ConsejoCard(
                    titulo = "Aumenta tus calorías",
                    descripcion = "Consume alimentos ricos en nutrientes y calorías saludables como frutos secos, aguacate y aceite de oliva.",
                    icono = Icons.Default.Restaurant
                )
                ConsejoCard(
                    titulo = "Entrenamiento de fuerza",
                    descripcion = "Realiza ejercicios de pesas para aumentar tu masa muscular de forma saludable.",
                    icono = Icons.Default.DirectionsRun
                )
            }
            "Normal" -> {
                ConsejoCard(
                    titulo = "¡Sigue así!",
                    descripcion = "Mantén una dieta equilibrada con suficientes proteínas, carbohidratos complejos y grasas saludables.",
                    icono = Icons.Default.CheckCircle
                )
                ConsejoCard(
                    titulo = "Actividad regular",
                    descripcion = "Realiza al menos 150 minutos de actividad aeróbica moderada a la semana para mantener tu corazón sano.",
                    icono = Icons.Default.Favorite
                )
            }
            "Sobrepeso" -> {
                ConsejoCard(
                    titulo = "Déficit calórico ligero",
                    descripcion = "Intenta reducir pequeñas porciones en tus comidas e incrementa el consumo de verduras y agua.",
                    icono = Icons.Default.Restaurant
                )
                ConsejoCard(
                    titulo = "Cardio y fuerza",
                    descripcion = "Combina ejercicios cardiovasculares con entrenamiento de fuerza para mejorar tu metabolismo.",
                    icono = Icons.Default.DirectionsRun
                )
            }
            "Obesidad" -> {
                ConsejoCard(
                    titulo = "Consulta a un especialista",
                    descripcion = "Es muy importante que un médico o nutriólogo evalúe tu caso para crear un plan seguro y personalizado.",
                    icono = Icons.Default.Warning
                )
                ConsejoCard(
                    titulo = "Empieza poco a poco",
                    descripcion = "Inicia con caminatas cortas y actividades de bajo impacto para proteger tus articulaciones.",
                    icono = Icons.Default.DirectionsRun
                )
            }
            else -> {
                Text("Por favor, actualiza tu perfil para ver consejos personalizados.")
            }
        }
    }
}