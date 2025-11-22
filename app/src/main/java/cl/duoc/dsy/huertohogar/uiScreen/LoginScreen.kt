package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.dsy.huertohogar.R
import cl.duoc.dsy.huertohogar.navegacion.AppScreens
import cl.duoc.dsy.huertohogar.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    // <-- CORRECCIÓN: Capturamos el estado en una variable local inmutable.
    val currentState = state

    LaunchedEffect(currentState.loginSuccess) {
        if (currentState.loginSuccess) {
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
            supportingText = {

                currentState.emailError?.let { error ->
                    Text(error)
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

                currentState.passError?.let { error ->
                    Text(error)
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro
        TextButton(
            onClick = { navController.navigate(AppScreens.RegisterScreen.route) },
            enabled = !currentState.isLoading
        ) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}