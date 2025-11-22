package cl.duoc.dsy.huertohogar.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    // --- Eventos de UI ---
    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(pass: String) {
        _state.update { it.copy(pass = pass, passError = null, repeatPassError = null) }
    }

    fun onRepeatPasswordChange(pass: String) {
        _state.update { it.copy(repeatPass = pass, repeatPassError = null) }
    }

    // --- Lógica de Validación (IE 2.1.2) ---
    private fun validate(): Boolean {
        val email = _state.value.email
        val pass = _state.value.pass
        val repeatPass = _state.value.repeatPass

        var hasError = false

        // 1. Las contraseñas no coinciden
        if (pass != repeatPass) {
            _state.update {
                it.copy(repeatPassError = "Las contraseñas no coinciden")
            }
            hasError = true
        }

        // 2. Contraseña muy corta (ej. < 6 caracteres)
        if (pass.length < 6) {
            _state.update {
                it.copy(passError = "La contraseña debe tener al menos 6 caracteres")
            }
            hasError = true
        }

        // 3. Formato de Email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.update {
                it.copy(emailError = "El formato del correo no es válido")
            }
            hasError = true
        }

        return !hasError // 'true' si NO hay errores
    }

    // --- Evento de Click ---
    fun onRegisterClicked() {
        if (!validate()) {
            return // Detener si la validación falla
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: Aquí iría la llamada al Repository para crear el usuario
            kotlinx.coroutines.delay(2000)

            // Simular éxito
            _state.update { it.copy(isLoading = false, registerSuccess = true) }
        }
    }
}