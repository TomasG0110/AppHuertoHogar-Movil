package cl.duoc.dsy.huertohogar.repository

import cl.duoc.dsy.huertohogar.R
import cl.duoc.dsy.huertohogar.model.Producto

// IE 2.3.1: Repositorio para centralizar origen de datos
class ProductoRepository {

    // Simulación de la base de datos con los productos del caso
    private val productosSimulados = listOf(
        Producto(
            id = "FR001",
            nombre = "Manzanas Fuji",
            descripcion = "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule.",
            precioPorKilo = 1200.0,
            stockEnKilos = 150,
            categoria = "Frutas Frescas",
            imagenResId = R.drawable.manzanas_fuji // Asegúrate que este nombre coincida
        ),
        Producto(
            id = "FR002",
            nombre = "Naranjas Valencia",
            descripcion = "Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para zumos frescos.",
            precioPorKilo = 1000.0,
            stockEnKilos = 200,
            categoria = "Frutas Frescas",
            imagenResId = R.drawable.naranjas_valencia
        ),
        Producto(
            id = "FR003",
            nombre = "Plátanos Cavendish",
            descripcion = "Plátanos maduros y dulces, perfectos para el desayuno o como snack energético.",
            precioPorKilo = 800.0,
            stockEnKilos = 250,
            categoria = "Frutas Frescas",
            imagenResId = R.drawable.platanos_cavendish
        ),
        Producto(
            id = "VR001",
            nombre = "Zanahorias Orgánicas",
            descripcion = "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins.",
            precioPorKilo = 900.0,
            stockEnKilos = 100,
            categoria = "Verduras Orgánicas",
            imagenResId = R.drawable.zanahorias_organicas
        ),
        Producto(
            id = "VR002",
            nombre = "Espinacas Frescas",
            descripcion = "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes.",
            precioPorKilo = 700.0, // El precio es por bolsa de 500g, lo adaptamos
            stockEnKilos = 80,
            categoria = "Verduras Orgánicas",
            imagenResId = R.drawable.espinacas_frescas
        ),
        Producto(
            id = "VR003",
            nombre = "Pimientos Tricolores",
            descripcion = "Pimientos rojos, amarillos y verdes, ideales para salteados y platos coloridos.",
            precioPorKilo = 1500.0,
            stockEnKilos = 120,
            categoria = "Verduras Orgánicas",
            imagenResId = R.drawable.pimientos_tricolor
        ),
        Producto(
            id = "PO001",
            nombre = "Miel Orgánica",
            descripcion = "Miel pura y orgánica producida por apicultores locales.",
            precioPorKilo = 5000.0, // Precio por frasco 500g
            stockEnKilos = 50,
            categoria = "Productos Orgánicos",
            imagenResId = R.drawable.miel_organica
        )
        // ... (Puedes agregar el resto de productos del caso)
    )

    // Función que la UI (a través del ViewModel) usará para obtener los productos
    fun getProductos(): List<Producto> {
        // TODO: En un futuro, aquí se llamaría a Room o a una API
        return productosSimulados
    }

    // TODO: Implementar funciones para persistencia (Room) para el carrito (IE 2.3.1)
}
