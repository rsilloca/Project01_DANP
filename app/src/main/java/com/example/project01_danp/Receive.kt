package com.example.project01_danp

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * I would delete this crap, I don't know why it doesn't work
 */

class Receive : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(intent.getIntExtra(NOTIFICATION_ID, -1))
        finish()
    }

    companion object {
        const val NOTIFICATION_ID = "NOTIFICATION_ID"

        fun getDismissIntent( notificationId: Int, context: Context? ): PendingIntent {
            val intent = Intent(context, Receive::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(NOTIFICATION_ID, notificationId)
            return PendingIntent.getActivity( context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
            )
        }

        fun getMainActivity( notificationId: Int, context: Context? ): PendingIntent {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_ONE_SHOT)
            getDismissIntent(notificationId, context)
            return pendingIntent
        }
    }
}