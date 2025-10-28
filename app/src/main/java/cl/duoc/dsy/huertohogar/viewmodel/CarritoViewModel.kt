package cl.duoc.dsy.huertohogar.viewmodel

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.dsy.huertohogar.db.AppDatabase
import cl.duoc.dsy.huertohogar.model.CartItem
import cl.duoc.dsy.huertohogar.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import cl.duoc.dsy.huertohogar.MainActivity
import cl.duoc.dsy.huertohogar.R


class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CarritoRepository

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        // Inicializar DAO y Repository
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        cartRepository = CarritoRepository(cartDao)

        // Empezar a escuchar la base de datos
        observeCartItems()
    }

    fun onDeleteItemClicked(item: CartItem) {
        viewModelScope.launch {
            cartRepository.deleteItemFromCart(item)

            // ¡NO NECESITAMOS ACTUALIZAR EL ESTADO MANUALMENTE!
            // Room notificará al 'Flow' en 'observeCartItems',
            // y la UI se actualizará sola.
        }
    }

    fun onCheckoutClicked() {
        val context = getApplication<Application>()
        val channelId = "huertohogar_channel"

        // 1. Crear un intent para abrir la app al tocar la notificación
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // 2. Construir la notificación
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo_huertohogar) // ¡Asegúrate que exista en drawable!
            .setContentTitle("¡Pedido Confirmado!")
            .setContentText("Tu compra en HuertoHogar se ha realizado con éxito.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Acción al tocar
            .setAutoCancel(true) // Se cierra al tocarla

        // 3. Mostrar la notificación
        with(NotificationManagerCompat.from(context)) {
            // Revisar si tenemos permiso (aunque ya lo pedimos)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                notify(1, builder.build()) // '1' es el ID de esta notificación
            }
        }
    }


    // : Escuchar cambios de Room en tiempo real
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