package cl.duoc.dsy.huertohogar.viewmodel

import android.net.Uri


data class ProfileState(
    val address: String = "",
    val phone: String = "",


    // Almacenará la URI de la foto de perfil tomada
    val profileImageUri: Uri? = null,

    //  Retroalimentación visual
    val addressError: String? = null,
    val phoneError: String? = null,

    val isLoading: Boolean = false,
    val saveSuccess: Boolean = false
)