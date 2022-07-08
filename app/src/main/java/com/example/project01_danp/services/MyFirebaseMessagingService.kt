package com.example.project01_danp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.project01_danp.LoginActivity
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.Receive
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "Notification_channel"
const val channelName = "com.example.project01_danp.services"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if(message.notification != null){
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.project01_danp", R.layout.notification)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.icon, R.drawable.ic_soles)
        return remoteView
    }

    private fun generateNotification(title: String, message: String) {

        val code = 100 //random number :c

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, code, intent, PendingIntent.FLAG_ONE_SHOT)

        val mainActivityIntet: PendingIntent = Receive.getMainActivity(code, this)
        val dismissIntent: PendingIntent = Receive.getDismissIntent(code, this)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.alcancia_app)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.alcancia_app))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setCustomBigContentView(getRemoteView(title, message))
            .addAction(R.drawable.ic_launcher_background, "View", mainActivityIntet)
            .addAction(R.drawable.ic_launcher_background, "Dismiss", dismissIntent)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(code, builder)
    }

}

