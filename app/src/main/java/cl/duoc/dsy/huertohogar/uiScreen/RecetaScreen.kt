package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cl.duoc.dsy.huertohogar.data.remote.RecetaClient
import cl.duoc.dsy.huertohogar.model.Receta
import coil.compose.AsyncImage // Usamos Coil para cargar la URL de internet


@Composable
fun RecetasScreen() {
    // Estado local para la lista de recetas
    var recetas by remember { mutableStateOf<List<Receta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Llamada a la API al iniciar la pantalla
    LaunchedEffect(Unit) {
        try {
            val response = RecetaClient.service.getRecetas()
            recetas = response.meals
        } catch (e: Exception) {
            e.printStackTrace() // Manejo básico de error
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Ideas para Cocinar 🥗",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recetas) { receta ->
                    RecetaItem(receta)
                }
            }
        }
    }
}

@Composable
fun RecetaItem(receta: Receta) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Imagen desde URL usando Coil
            AsyncImage(
                model = receta.strMealThumb,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = receta.strMeal,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}