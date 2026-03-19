package com.example.saludapp.pagina1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saludapp.viewmodel.SaludViewModel
import com.example.saludapp.ui.theme.*

@Composable
fun FormularioScreen(viewModel: SaludViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        if (!viewModel.tienePerfil) {
            CrearPerfil(
                nombre = viewModel.nombre,
                onNombreChange = { viewModel.nombre = it },
                edad = viewModel.edad,
                onEdadChange = { viewModel.edad = it },
                estatura = viewModel.estatura,
                onEstaturaChange = { viewModel.estatura = it },
                peso = viewModel.peso,
                onPesoChange = { viewModel.peso = it },
                onGuardarPerfil = { viewModel.guardarPerfil() }
            )
        } else {
            val imcCalculado = viewModel.calcularIMC()
            val categoriaCalculada = viewModel.obtenerCategoriaIMC(imcCalculado)
            PerfilSaludDashboard(
                nombre = viewModel.nombre,
                edad = viewModel.edad,
                estatura = viewModel.estatura,
                peso = viewModel.peso,
                imc = imcCalculado,
                categoriaImc = categoriaCalculada,
                onEditarClick = { viewModel.editarPerfil() }
            )
        }
    }
}

@Composable
fun PerfilSaludDashboard(
    nombre: String,
    edad: String,
    estatura: String,
    peso: String,
    imc: Double,
    categoriaImc: String,
    onEditarClick: () -> Unit
) {
    val nombreMostrar = nombre.ifBlank { "Usuario" }
    val edadMostrar = if (edad.isNotBlank()) "$edad años" else "--"
    val estaturaMostrar = if (estatura.isNotBlank()) "$estatura cm" else "--"
    val pesoMostrar = if (peso.isNotBlank()) "$peso kg" else "--"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Perfil de Salud",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = onEditarClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Editar", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isDark = isSystemInDarkTheme()

            InfoRowCard(Icons.Default.Person, Color(0xFF3B82F6), if(isDark) Color(0xFF3B82F6).copy(alpha=0.15f) else Color(0xFFEFF6FF), "Nombre", nombreMostrar)
            InfoRowCard(Icons.Default.DateRange, Color(0xFFA855F7), if(isDark) Color(0xFFA855F7).copy(alpha=0.15f) else Color(0xFFFAF5FF), "Edad", edadMostrar)
            InfoRowCard(Icons.Default.Straighten, Color(0xFF22C55E), if(isDark) Color(0xFF22C55E).copy(alpha=0.15f) else Color(0xFFF0FDF4), "Estatura", estaturaMostrar)
            InfoRowCard(Icons.Default.MonitorWeight, Color(0xFFF97316), if(isDark) Color(0xFFF97316).copy(alpha=0.15f) else Color(0xFFFFF7ED), "Peso", pesoMostrar)

            IMCCard(imc = imc, categoria = categoriaImc)
        }
    }
}

@Composable
fun InfoRowCard(icon: ImageVector, iconTint: Color, bgColor: Color, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Composable
fun IMCCard(imc: Double, categoria: String) {
    val imcFormateado = if (imc > 0) String.format("%.1f", imc) else "--"

    val colorCategoria = when (categoria) {
        "Bajo peso" -> Color(0xFF3B82F6) // Azul
        "Normal" -> Color(0xFF22C55E) // Verde
        "Sobrepeso" -> Color(0xFFF97316) // Naranja
        "Obesidad" -> Color(0xFFEF4444) // Rojo
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Timeline,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF4F46E5), RoundedCornerShape(10.dp))
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Índice de Masa Corporal (IMC)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = imcFormateado, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = categoria, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colorCategoria, modifier = Modifier.padding(bottom = 3.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val minImc = 15f
                val maxImc = 40f
                val rangoImc = maxImc - minImc

                val progreso = if (imc > 0) {
                    ((imc.toFloat() - minImc) / rangoImc).coerceIn(0f, 1f)
                } else 0f

                val anchoTotal = maxWidth

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF3B82F6)))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF22C55E)))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFF97316)))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF4444)))
                    }

                    if (imc > 0) {
                        Box(
                            modifier = Modifier
                                .offset(x = (anchoTotal * progreso) - 6.dp)
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurface)
                                .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "15", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                Text(text = "40+", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
            }
        }
    }
}