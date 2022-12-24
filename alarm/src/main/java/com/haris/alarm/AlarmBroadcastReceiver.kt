package com.haris.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.haris.ui.R

const val ALARM_EXTRA_ID = "com.haris.alarm.id"
const val ALARM_EXTRA_TITLE = "com.haris.alarm.title"
const val ALARM_EXTRA_DESCRIPTION = "com.haris.alarm.description"
const val ALARM_EXTRA_DATE = "com.haris.alarm.date"

private const val CHANNEL_ID = "com.haris.alarm.channel"

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.extras?.getInt(ALARM_EXTRA_ID)
        val title = intent.extras?.getString(ALARM_EXTRA_TITLE)
        val desc = intent.extras?.getString(ALARM_EXTRA_DESCRIPTION)
        val date = intent.extras?.getString(ALARM_EXTRA_DATE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText("$desc\n$date")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                title,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = desc
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId ?: 0, builder.build())
        }
    }
}