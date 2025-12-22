package com.pedro_lopez.pds_iii_.Notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pedro_lopez.pds_iii_.Chat.ChatActivity
import com.pedro_lopez.pds_iii_.R
import java.util.Random

class MyFormService : FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "CHAT_KOTLIN_CHANNEL_ID"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null) {
            val senderUid = remoteMessage.data["senderUid"]

            showNotification(
                remoteMessage.notification?.title,
                remoteMessage.notification?.body,
                senderUid?: "Desconocido"
            )
        }
    }

    private fun showNotification(title: String?, body: String?, senderUid: String) {
        val notificationId = Random().nextInt(3000)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        configurarCanalNotification(notificationManager)

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("uid", senderUid)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.chat_notification)
            .setContentTitle(title ?: "Sin titulo")
            .setContentText(body ?: "Sin contenido")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun configurarCanalNotification(notificationManager : NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Chat_Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "Show Chat Notification"
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}