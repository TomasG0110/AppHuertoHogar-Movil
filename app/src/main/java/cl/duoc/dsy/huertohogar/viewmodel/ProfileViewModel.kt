package cl.duoc.dsy.huertohogar.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.net.Uri

//  Lógica desacoplada
class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    // IE 2.3.1: Almacenamiento Local (SharedPreferences)
    private val prefs = application.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfileData()
    }

    // Cargar datos guardados
    private fun loadProfileData() {
        val address = prefs.getString("USER_ADDRESS", "") ?: ""
        val phone = prefs.getString("USER_PHONE", "") ?: ""
        _state.update { it.copy(address = address, phone = phone) }
    }

    // --- Eventos de UI ---
    fun onAddressChange(address: String) {
        _state.update { it.copy(address = address, addressError = null, saveSuccess = false) }
    }

    fun onPhoneChange(phone: String) {
        // Permitir solo números y limitar longitud (ej. 9 dígitos)
        if (phone.all { it.isDigit() } && phone.length <= 9) {
            _state.update { it.copy(phone = phone, phoneError = null, saveSuccess = false) }
        }
    }
    fun onPhotoTaken(uri: Uri?) {
        _state.update { it.copy(profileImageUri = uri) }
        // TODO: En un futuro, guardar esta URI en SharedPreferences
    }

    // --- Validación  ---
    private fun validate(): Boolean {
        val address = _state.value.address
        val phone = _state.value.phone
        var hasError = false

        if (address.isBlank()) {
            _state.update { it.copy(addressError = "La dirección no puede estar vacía") }
            hasError = true
        }

        // Validación simple de teléfono chileno
        if (phone.length != 9) {
            _state.update { it.copy(phoneError = "El teléfono debe tener 9 dígitos (ej: 912345678)") }
            hasError = true
        }

        return !hasError
    }

    fun onSaveClicked() {
        if (!validate()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Guardar en SharedPreferences
            prefs.edit()
                .putString("USER_ADDRESS", _state.value.address)
                .putString("USER_PHONE", _state.value.phone)
                .apply() // .apply() es asíncrono

            kotlinx.coroutines.delay(1000) // Simular guardado

            _state.update { it.copy(isLoading = false, saveSuccess = true) }
        }
    }
}