package cl.duoc.dsy.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.dsy.huertohogar.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    // 1. Instancia del Repositorio
    private val repository = ProductoRepository()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // 3. El bloque init se ejecuta cuando el ViewModel es creado
    init {
        loadProductos()
    }

    // 2. Lógica para cargar productos
    private fun loadProductos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: Agregar delay(1000) si queremos simular carga de red

            val productos = repository.getProductos()

            _state.update {
                it.copy(isLoading = false, productos = productos)
            }
        }
    }
}