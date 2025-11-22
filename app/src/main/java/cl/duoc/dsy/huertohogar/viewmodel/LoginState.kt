package cl.duoc.dsy.huertohogar.viewmodel

// Data class que representa el estado de la pantalla de Login
data class LoginState(
    val email: String = "",
    val pass: String = "",

    // Para validaciones IE 2.1.2
    val emailError: String? = null,
    val passError: String? = null,

    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)