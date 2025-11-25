package cl.duoc.dsy.huertohogar

import cl.duoc.dsy.huertohogar.model.Administrador
import cl.duoc.dsy.huertohogar.model.Cliente
import cl.duoc.dsy.huertohogar.utils.SessionManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionManagerTest {

    @Test
    fun `al iniciar sesion como Admin, el usuario debe ser de tipo Administrador`() {
        // 1. Acción (Given)
        SessionManager.iniciarSesion("Admin")

        // 2. Verificación (Then)
        val usuarioActual = SessionManager.usuarioActual.value

        // Verificamos que no sea nulo
        assertTrue(usuarioActual != null)

        // Verificamos que sea de la clase correcta (Polimorfismo)
        assertTrue(usuarioActual is Administrador)

        // Verificamos el rol
        assertEquals("Admin", usuarioActual?.rol)
    }

    @Test
    fun `al iniciar sesion como Cliente, no debe tener acceso a Tiendas`() {
        // 1. Acción
        SessionManager.iniciarSesion("Cliente")
        val usuario = SessionManager.usuarioActual.value

        // 2. Verificación
        // Un cliente NO debería tener la ruta "stores" en su menú
        val menu = usuario?.obtenerMenuNavegacion()

        val tieneAccesoATiendas = menu?.any { it.route == "stores" } ?: false

        // Esperamos que sea falso
        assertEquals(false, tieneAccesoATiendas)
    }
}