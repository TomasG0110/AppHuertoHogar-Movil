package cl.duoc.dsy.huertohogar.viewmodel

import cl.duoc.dsy.huertohogar.model.Producto

data class HomeState(
    val isLoading: Boolean = true,
    val productos: List<Producto> = emptyList()
)