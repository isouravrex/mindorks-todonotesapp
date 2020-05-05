package com.mindorks.todonotesapp.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mindorks.todonotesapp.R

//@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService(){
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        setUpNotification(message.notification?.body)
    }

    private fun setUpNotification(body: String?) {
        val channelId = "Yodo ID"
        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Todo Notes App")
                .setContentText(body)
                .setSound(ringtoneManager)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"Todo-Notes",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}