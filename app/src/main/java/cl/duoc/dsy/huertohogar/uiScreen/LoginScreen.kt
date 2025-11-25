package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.dsy.huertohogar.R
import cl.duoc.dsy.huertohogar.navegacion.AppScreens
import cl.duoc.dsy.huertohogar.viewmodel.LoginViewModel
import cl.duoc.dsy.huertohogar.utils.SessionManager


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    LaunchedEffect(currentState.loginSuccess) {
        if (currentState.loginSuccess) {
            SessionManager.iniciarSesion("Cliente")
            navController.navigate(AppScreens.MainScreen.route) {
                popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_huertohogar),
            contentDescription = "Logo HuertoHogar",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Email (usando la variable local 'currentState')
        OutlinedTextField(
            value = currentState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = currentState.emailError != null,
            supportingText = { currentState.emailError?.let { Text(it)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña (usando la variable local 'currentState')
        OutlinedTextField(
            value = currentState.pass,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = currentState.passError != null,
            supportingText = {
                currentState.passError?.let { Text(it)
                }
            }
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = { navController.navigate(AppScreens.RecoveryScreen.route) }) {
                Text("¿Olvidaste tu contraseña?", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Ingreso
        Button(
            onClick = { viewModel.onLoginClicked() },
            enabled = !currentState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (currentState.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Ingresar")
            }
        }

        TextButton(
            onClick = { navController.navigate(AppScreens.RegisterScreen.route) },
            enabled = !currentState.isLoading
        ) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }


        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Acceso Rápido (Demo Roles)",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón Admin
            Button(
                onClick = {
                    SessionManager.iniciarSesion("Admin")
                    navController.navigate(AppScreens.MainScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) { Text("Admin", fontSize = 10.sp) }

            // Botón Cliente
            Button(
                onClick = {
                    SessionManager.iniciarSesion("Cliente")
                    navController.navigate(AppScreens.MainScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) { Text("Cliente", fontSize = 10.sp) }

            // Botón Repartidor
            Button(
                onClick = {
                    SessionManager.iniciarSesion("Repartidor")
                    navController.navigate(AppScreens.MainScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) { Text("Reparto", fontSize = 10.sp) }
        }
    }
}




