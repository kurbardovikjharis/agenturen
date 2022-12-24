package com.haris.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject

class AlarmManager @Inject constructor(
    private val context: Context
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarm(
        id: Int,
        isDaily: Boolean,
        title: String,
        description: String,
        time: String,
        timeInMillis: Long
    ) {
        val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

        if (canScheduleExactAlarms) {
            alarmManager.setRepeating(
                AlarmManager.RTC,
                timeInMillis,
                60000,
//                if (isDaily) AlarmManager.INTERVAL_DAY else (AlarmManager.INTERVAL_DAY * 7),
                createAlarmIntent(
                    id = id,
                    title = title,
                    description = description,
                    time = time
                )
            )
        }
    }

    fun cancelAlarm(id: Int) {
        alarmManager.cancel(createAlarmIntent(id))
    }

    private fun createAlarmIntent(
        id: Int,
        title: String = "",
        description: String = "",
        time: String = ""
    ): PendingIntent {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(ALARM_EXTRA_ID, id)
            putExtra(ALARM_EXTRA_TITLE, title)
            putExtra(ALARM_EXTRA_DESCRIPTION, description)
            putExtra(ALARM_EXTRA_TIME, time)
        }
        return PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}