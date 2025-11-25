package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.dsy.huertohogar.model.Producto
import cl.duoc.dsy.huertohogar.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import cl.duoc.dsy.huertohogar.R
import cl.duoc.dsy.huertohogar.utils.SessionManager

// 1. PANTALLA PRINCIPAL
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    val usuario by SessionManager.usuarioActual.collectAsState()
    val esAdmin = usuario?.rol == "Admin"
    val esInvitado = usuario?.rol == "Visita"

    var showDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Producto?>(null) }

    // --- Diálogo de Edición ---
    if (showDialog && productToEdit != null) {
        EditProductDialog(
            producto = productToEdit!!,
            onDismiss = { showDialog = false },
            onConfirm = { nuevoNombre, nuevoPrecio ->
                viewModel.onUpdateProduct(productToEdit!!.id, nuevoNombre, nuevoPrecio)
                showDialog = false
            }
        )
    }

    // --- Snackbar ---
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentState.productAddedMessage) {
        currentState.productAddedMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
            viewModel.onMessageShown()
        }
    }

    // --- UI Principal ---
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            // Solo mostramos el botón flotante si es Admin (opcional, según tu criterio)
            if (esAdmin) {
                FloatingActionButton(
                    onClick = { viewModel.onAddDummyProduct() },
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Crear Producto en Server")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                            esAdmin = esAdmin,
                            esInvitado = esInvitado,
                            onAddToCart = { viewModel.onAddToCartClicked(producto) },
                            onDelete = { viewModel.onDeleteProduct(producto) },
                            onUpdate = {
                                productToEdit = producto
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}

// 2. TARJETA DE PRODUCTO
@Composable
fun ProductoItem(
    producto: Producto,
    esAdmin: Boolean,
    esInvitado: Boolean,
    onAddToCart: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageResId = remember(producto.imagenNombre) {
        context.resources.getIdentifier(
            producto.imagenNombre,
            "drawable",
            context.packageName
        )
    }
    val imagenFinal = if (imageResId != 0) imageResId else R.drawable.ic_launcher_foreground

    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    format.maximumFractionDigits = 0
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
                    Row {
                        // Solo Admin ve editar/borrar
                        if (esAdmin) {
                            IconButton(onClick = { onUpdate() }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            IconButton(onClick = { onDelete() }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Borrar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        // Solo si NO es invitado, puede comprar
                        if (!esInvitado) {
                            IconButton(onClick = { onAddToCart() }) {
                                Icon(Icons.Default.AddShoppingCart, contentDescription = "Añadir")
                            }
                        }
                    }
                }
            }
        }
    }
}

// 3. DIÁLOGO DE EDICIÓN (¡AHORA ESTÁ AFUERA!)
@Composable
fun EditProductDialog(
    producto: Producto,
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var precioStr by remember { mutableStateOf(producto.precio.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Editar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = precioStr,
                    onValueChange = { precioStr = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val precioInt = precioStr.toIntOrNull() ?: 0
                onConfirm(nombre, precioInt)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}