package cl.duoc.dsy.huertohogar.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // _state es privado y mutable, solo el ViewModel lo puede cambiar
    private val _state = MutableStateFlow(LoginState())

    // state es público e inmutable, la UI solo puede leerlo
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Evento: El usuario escribió en el campo email
    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, emailError = null) }
    }

    // Evento: El usuario escribió en el campo contraseña
    fun onPasswordChange(pass: String) {
        _state.update { it.copy(pass = pass, passError = null) }
    }

    private fun validate(): Boolean {
        val email = _state.value.email
        val pass = _state.value.pass

        var hasError = false

        // 1. Validación de Contraseña
        if (pass.isBlank()) {
            _state.update {
                it.copy(passError = "La contraseña no puede estar vacía")
            }
            hasError = true
        }

        // 2. Validación de Email (Formato)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.update {
                it.copy(emailError = "El formato del correo no es válido")
            }
            hasError = true
        }

        // 3. Validación de Email (Vacío)
        if (email.isBlank()) {
            _state.update {
                it.copy(emailError = "El correo no puede estar vacío")
            }
            hasError = true
        }

        return !hasError // Retorna 'true' si NO hay errores
    }

    fun onLoginClicked() {
        // 1. Validar primero
        if (!validate()) {
            return // Detener si la validación falla
        }

        // 2. Si la validación es exitosa, proceder
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: Aquí iría la llamada al Repository
            // (Simulamos una pequeña demora)
            kotlinx.coroutines.delay(2000)

            // 3. Simular éxito
            _state.update { it.copy(isLoading = false, loginSuccess = true) }
        }
    }
}