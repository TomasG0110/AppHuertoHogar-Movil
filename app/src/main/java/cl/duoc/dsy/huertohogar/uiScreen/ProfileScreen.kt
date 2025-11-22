package cl.duoc.dsy.huertohogar.uiScreen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.dsy.huertohogar.BuildConfig
import cl.duoc.dsy.huertohogar.viewmodel.ProfileViewModel
import coil.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//  Implementación de recurso nativo (Cámara)

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    val context = LocalContext.current

    // --- Lógica de Cámara ---

    // 1. Variable temporal para guardar la URI de la foto que VAMOS a tomar
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // 2. Lanzador de la cámara (espera el resultado de la foto)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // La foto se guardó en 'tempPhotoUri', ahora la pasamos al ViewModel
            viewModel.onPhotoTaken(tempPhotoUri)
        }
    }

    // 3. Lanzador del permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permiso concedido, lanzar la cámara
            val uri = createImageUri(context)
            tempPhotoUri = uri
            cameraLauncher.launch(uri)
        } else {
            // TODO: Mostrar un mensaje al usuario de que el permiso es necesario
        }
    }

    // --- Interfaz de Usuario ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // --- Círculo de Foto de Perfil  ---
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable {
                    // 4. Lógica al hacer clic en la foto
                    when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                        PackageManager.PERMISSION_GRANTED -> {
                            // Permiso ya concedido, lanzar cámara
                            val uri = createImageUri(context)
                            tempPhotoUri = uri
                            cameraLauncher.launch(uri)
                        }
                        else -> {
                            // Pedir permiso
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (currentState.profileImageUri != null) {
                // Mostrar la foto tomada (usando Coil)
                AsyncImage(
                    model = currentState.profileImageUri,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Tomar foto",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text("Toca la cámara para tomar una foto", style = MaterialTheme.typography.bodySmall)

        // --- Campo Dirección (IE 2.1.2) ---
        OutlinedTextField(
            value = currentState.address,
            onValueChange = { viewModel.onAddressChange(it) },
            // ... (resto de OutlinedTextField de dirección)
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Dirección de Entrega") },
            isError = currentState.addressError != null,
            supportingText = { currentState.addressError?.let { Text(it) } },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = "Dirección") }
        )

        // --- Campo Teléfono (IE 2.1.2) ---
        OutlinedTextField(
            value = currentState.phone,
            onValueChange = { viewModel.onPhoneChange(it) },
            // ... (resto de OutlinedTextField de teléfono)
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Teléfono de Contacto") },
            prefix = { Text("+56 ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = currentState.phoneError != null,
            supportingText = { currentState.phoneError?.let { Text(it) } },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Teléfono") }
        )

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

        // --- Botón Guardar ---
        Button(
            onClick = { viewModel.onSaveClicked() },
            // ... (resto del botón)
            enabled = !currentState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (currentState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Guardar Cambios")
            }
        }

        // Mensaje de éxito
        if (currentState.saveSuccess) {
            Text(
                "¡Datos guardados con éxito!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Función helper para crear una URI segura para la cámara
private fun createImageUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFile = File(context.cacheDir, "JPEG_${timeStamp}_.jpg")
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider", // Debe coincidir con el AndroidManifest
        imageFile
    )
}