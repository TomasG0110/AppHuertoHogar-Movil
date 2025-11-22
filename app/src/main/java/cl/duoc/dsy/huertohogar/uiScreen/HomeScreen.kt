package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.dsy.huertohogar.model.Producto
import cl.duoc.dsy.huertohogar.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import cl.duoc.dsy.huertohogar.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    // --- 1. Configuración para el Snackbar ---
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // --- 2. Observador para mostrar el mensaje ---
    // (IE 2.2.2: Retroalimentación visual)
    LaunchedEffect(currentState.productAddedMessage) {
        currentState.productAddedMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
            // Limpiamos el mensaje en el ViewModel
            viewModel.onMessageShown()
        }
    }

    // --- 3. Envolvemos la UI en un Scaffold ---
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddDummyProduct()},
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Producto en Server")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Aplicar padding del Scaffold
            contentAlignment = Alignment.Center
        ) {
            if (currentState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentState.productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onAddToCart = { viewModel.onAddToCartClicked(producto) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, onAddToCart: () -> Unit ) {

    // 1. Obtener contexto para buscar recursos
    val context = androidx.compose.ui.platform.LocalContext.current

    // 2. Buscar el ID de la imagen usando el nombre que viene del JSON
    // (Ej: busca un drawable llamado "manzanas_fuji")
    val imageResId = remember(producto.imagenNombre) {
        context.resources.getIdentifier(
            producto.imagenNombre,
            "drawable",
            context.packageName
        )
    }

    // 3. Si no encuentra la imagen (id es 0), usa un ícono por defecto para que no falle
    val imagenFinal = if (imageResId != 0) imageResId else R.drawable.ic_launcher_foreground // O tu logo


    val format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CL"))
    format.maximumFractionDigits = 0
    // OJO: Ahora usamos 'producto.precio' (Int)
    val precioFormateado = format.format(producto.precio)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imagenFinal),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$precioFormateado / kg",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Button(onClick = {
                        onAddToCart()
                    }) {
                        Icon(
                            Icons.Default.AddShoppingCart,
                            contentDescription = "Agregar al Carrito"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Añadir")
                    }
                }
            }
        }
    }
}