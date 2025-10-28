package cl.duoc.dsy.huertohogar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.dsy.huertohogar.db.AppDatabase
import cl.duoc.dsy.huertohogar.model.CartItem
import cl.duoc.dsy.huertohogar.repository.CaritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CaritoRepository

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        // Inicializar DAO y Repository
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        cartRepository = CaritoRepository(cartDao)

        // Empezar a escuchar la base de datos
        observeCartItems()
    }

    // IE 2.3.1: Escuchar cambios de Room en tiempo real
    private fun observeCartItems() {
        viewModelScope.launch {
            // 'collectLatest' se ejecutará cada vez que la DB cambie
            cartRepository.getAllCartItems().collectLatest { items ->
                // Calcular el precio total
                val total = items.sumOf { it.precioPorKilo * it.quantity }

                // Actualizar el estado de la UI
                _state.update {
                    it.copy(items = items, totalPrice = total)
                }
            }
        }
    }

    // TODO: Implementar lógica para remover, aumentar o disminuir cantidad
}