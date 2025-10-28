package cl.duoc.dsy.huertohogar.viewmodel

// 1. Importar lo necesario
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.dsy.huertohogar.db.AppDatabase
import cl.duoc.dsy.huertohogar.model.Producto
import cl.duoc.dsy.huertohogar.repository.CarritoRepository
import cl.duoc.dsy.huertohogar.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 2. Cambiar 'ViewModel' por 'AndroidViewModel(application)'
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorio de Productos (simulado)
    private val productoRepository = ProductoRepository()

    // Repositorio de Carrito (conectado a Room)
    private val carritoRepository: CarritoRepository

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // 3. Inicializar el DAO y el Repositorio del Carrito
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        carritoRepository = CarritoRepository(cartDao)

        loadProductos()
    }

    private fun loadProductos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val productos = productoRepository.getProductos()
            _state.update {
                it.copy(isLoading = false, productos = productos)
            }
        }
    }

    // 4. Nueva función paraAñadir al Carrito (IE 2.3.1)
    fun onAddToCartClicked(producto: Producto) {
        viewModelScope.launch {
            carritoRepository.addProductoToCart(producto)
            _state.update { it.copy(productAddedMessage = "Producto añadido al carrito") }
        }
    }
    // La UI llamará a esto después de mostrar el mensaje
    fun onMessageShown() {
        _state.update { it.copy(productAddedMessage = null) }
    }
}