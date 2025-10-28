package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.dsy.huertohogar.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel() // 1. Inyectar ViewModel
) {

    val state by viewModel.state.collectAsState()

    // Capturamos estado local para evitar "Smart cast"
    val currentState = state

    // 2. Reaccionar al éxito del registro
    LaunchedEffect(currentState.registerSuccess) {
        if (currentState.registerSuccess) {
            // Si es exitoso, volver al Login (no limpiar historial)
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            // Barra superior con botón de "volver"
            TopAppBar(
                title = { Text("Crear Cuenta") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Verde
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Blanco
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Regístrate",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary // Marron
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Campo de Email ---
            OutlinedTextField(
                value = currentState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = currentState.emailError != null,
                supportingText = {
                    currentState.emailError?.let { Text(it) }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Campo de Contraseña ---
            OutlinedTextField(
                value = currentState.pass,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña (mín. 6 caracteres)") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = currentState.passError != null,
                supportingText = {
                    currentState.passError?.let { Text(it) }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Campo de Repetir Contraseña ---
            OutlinedTextField(
                value = currentState.repeatPass,
                onValueChange = { viewModel.onRepeatPasswordChange(it) },
                label = { Text("Repetir Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = currentState.repeatPassError != null,
                supportingText = {
                    currentState.repeatPassError?.let { Text(it) }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Botón de Registro ---
            Button(
                onClick = { viewModel.onRegisterClicked() },
                enabled = !currentState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (currentState.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Registrarme")
                }
            }
        }
    }
}