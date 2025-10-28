package cl.duoc.dsy.huertohogar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.dsy.huertohogar.navegacion.AppNavigation
import cl.duoc.dsy.huertohogar.ui.theme.HuertoHogarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        enableEdgeToEdge()
        setContent {
            HuertoHogarTheme {

                AppNavigation()
            }
        }
    }
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "huertohogar_channel"
            val name = "Pedidos HuertoHogar"
            val descriptionText = "Notificaciones sobre el estado de tus pedidos."
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            // Registrar el canal en el sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
