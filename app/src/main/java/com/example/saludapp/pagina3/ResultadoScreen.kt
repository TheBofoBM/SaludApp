package com.example.saludapp.pagina3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saludapp.viewmodel.SaludViewModel

@Composable
fun ResultadoScreen(viewModel: SaludViewModel, modifier: Modifier = Modifier) {
    val imc = viewModel.calcularIMC()
    val pesoIdeal = viewModel.calcularPesoIdeal()
    val pesoActual = viewModel.peso.toDoubleOrNull() ?: 0.0
    val diferenciaPeso = pesoIdeal - pesoActual

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
            }
            Text(
                text = "SaludApp",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Rastrea tu información de salud y bienestar",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Card Mi Progreso
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFCE93D8), RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Mi Progreso", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // IMC Card
                InfoCard(
                    title = "IMC Actual",
                    value = String.format("%.1f", imc),
                    icon = Icons.Default.Timeline,
                    iconColor = Color(0xFF3F51B5),
                    backgroundColor = Color(0xFFE3F2FD)
                ) {
                    Column {
                        IMCProgressBar(imc)
                        Text(
                            text = viewModel.obtenerMensajeIMC(imc),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Peso Ideal Card
                InfoCard(
                    title = "Peso Ideal",
                    value = String.format("%.1f kg", pesoIdeal),
                    icon = Icons.Default.Adjust,
                    iconColor = Color(0xFF2E7D32),
                    backgroundColor = Color(0xFFE8F5E9)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Peso actual: ${viewModel.peso} kg", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            text = String.format("%+.1f kg", diferenciaPeso),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (diferenciaPeso >= 0) Color(0xFF1E88E5) else Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Objetivos Saludables
                Text(text = "Objetivos Saludables", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFCE4EC).copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ObjetivoItem("Beber 8 vasos de agua diarios")
                        ObjetivoItem("30 minutos de ejercicio al día")
                        ObjetivoItem("7-8 horas de sueño")
                        ObjetivoItem("5 porciones de frutas/verduras")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4).copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "💡", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Tip: Registra tu progreso semanalmente para ver tu evolución",
                    fontSize = 13.sp,
                    color = Color(0xFF5D4037)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor.copy(alpha = 0.3f))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1A237E))
                }
                Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun IMCProgressBar(imc: Double) {
    val progress = (imc / 40.0).coerceIn(0.0, 1.0).toFloat()
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(CircleShape)
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)
                    )
                )
        )
    }
}

@Composable
fun ObjetivoItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(0xFF7B1FA2))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontSize = 14.sp, color = Color(0xFF455A64))
    }
}
