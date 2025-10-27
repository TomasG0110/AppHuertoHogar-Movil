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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.dsy.huertohogar.R
import cl.duoc.dsy.huertohogar.navegacion.AppScreens

@Composable
fun LoginScreen(navController: NavController) {

    // Estados locales para guardar el texto de los campos
    // TODO: Mover estos estados al ViewModel (IE 2.2.1)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Fondo BlancoSuave
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo (IE 2.1.1 Coherencia Visual)
        Image(
            painter = painterResource(id = R.drawable.Logo_HuertoHogar),
            contentDescription = "Logo HuertoHogar",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título (Usando los colores y tipografía del Tema)
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium, // Tipografía Playfair (si la configuraste)
            color = MaterialTheme.colorScheme.tertiary // Color MarronClaro
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Email (IE 2.1.2 Formulario)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            // TODO: Añadir ícono y mensaje de error de validación (IE 2.1.2)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña (IE 2.1.2 Formulario)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            // TODO: Añadir ícono y mensaje de error de validación (IE 2.1.2)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Ingreso (IE 2.1.1 Navegación)
        Button(
            onClick = {
                // TODO: Implementar lógica de login en ViewModel (IE 2.2.1)

                // Navegación temporal
                navController.navigate(AppScreens.MainScreen.route) {
                    // Limpia el historial para que no pueda volver al Login
                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            // El color del botón se toma de 'primary' (VerdeEsmeralda)
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro (IE 2.1.1 Navegación)
        TextButton(onClick = {
            navController.navigate(AppScreens.RegisterScreen.route)
        }) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}