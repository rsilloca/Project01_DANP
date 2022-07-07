package com.example.project01_danp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * I would delete this crap, I don't know why it doesn't work
 */

class Receive : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val notification = intent.getIntExtra("notification", -1)
            if (notification > 0) {
                val notificationManager = context
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(notification)
            }
        }
    }
}