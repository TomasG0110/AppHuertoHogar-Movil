package cl.duoc.dsy.huertohogar.viewmodel

// Data class que representa el estado de la pantalla de Registro
data class RegisterState(
    val email: String = "",
    val pass: String = "",
    val repeatPass: String = "",

    // Para validaciones IE 2.1.2
    val emailError: String? = null,
    val passError: String? = null,
    val repeatPassError: String? = null,

    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false
)