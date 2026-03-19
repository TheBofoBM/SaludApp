package com.example.saludapp.pagina1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CrearPerfil(
    nombre: String, onNombreChange: (String) -> Unit,
    edad: String, onEdadChange: (String) -> Unit,
    estatura: String, onEstaturaChange: (String) -> Unit,
    peso: String, onPesoChange: (String) -> Unit,
    onGuardarPerfil: () -> Unit
) {
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
            Text(
                text = "Crea Tu Perfil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            CampoFormulario("Nombre", Icons.Outlined.Person, "Ingresa tu nombre", nombre, onNombreChange)
            CampoFormulario("Edad (años)", Icons.Outlined.DateRange, "Ingresa tu edad", edad, onEdadChange, true)
            CampoFormulario("Estatura (cm)", Icons.Outlined.Straighten, "Ingresa tu estatura", estatura, onEstaturaChange, true)
            CampoFormulario("Peso (kg)", Icons.Outlined.MonitorWeight, "Ingresa tu peso", peso, onPesoChange, true)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGuardarPerfil,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Perfil", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFormulario(
    etiqueta: String,
    icono: ImageVector,
    placeholder: String,
    valor: String,
    onValorChange: (String) -> Unit,
    esNumero: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = etiqueta, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esNumero) KeyboardType.Number else KeyboardType.Text
            ),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = if (esNumero) {
                { Icon(Icons.Outlined.UnfoldMore, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
            } else null
        )
    }
}