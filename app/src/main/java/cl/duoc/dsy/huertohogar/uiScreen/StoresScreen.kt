package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun StoresScreen(){
    val tiendas = listOf(
        "Santiago Centro - Av. Providencia 1234",
        "Viña del Mar - Calle Valparaíso 555",
        "Valparaíso - Av. Pedro Montt 1800",
        "Concepción - Av. O'Higgins 987",
        "Puerto Montt - Calle Varas 400",
        "Villarrica - Av. Pedro de Valdivia 800",
        "Nacimiento - Calle Freire 300"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nuestras Sucursales",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Encuentra tus productos frescos en nuestros 9 puntos a lo largo del país.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tiendas) { tienda ->
                StoreItem(tienda)
            }
        }
    }
}

@Composable
fun StoreItem(direccion: String) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de Tienda
            Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // Título (Ciudad) y Subtítulo (Dirección)
                val partes = direccion.split(" - ")
                val ciudad = partes.getOrElse(0) { "Sucursal" }
                val calle = partes.getOrElse(1) { direccion }

                Text(
                    text = ciudad,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = calle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Ícono de "Ir" o ubicación
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Ver mapa",
                tint = Color.Gray
            )
        }
    }
}

