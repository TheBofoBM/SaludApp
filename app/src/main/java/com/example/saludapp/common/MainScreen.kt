package com.example.saludapp.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.saludapp.pagina1.FormularioScreen
import com.example.saludapp.pagina2.ConsejosScreen
import com.example.saludapp.pagina3.ResultadoScreen

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Consejos") },
                    label = { Text("Consejos") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Progreso") },
                    label = { Text("Progreso") }
                )
            }
        }
    ) { innerPadding ->
        ContentArea(
            selectedTab = selectedTab,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentArea(selectedTab: Int, modifier: Modifier = Modifier) {
    when (selectedTab) {
        0 -> FormularioScreen(modifier)
        1 -> ConsejosScreen(modifier)
        2 -> ResultadoScreen(modifier)
    }
}
