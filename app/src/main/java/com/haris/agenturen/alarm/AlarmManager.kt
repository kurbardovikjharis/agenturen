package com.haris.agenturen.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject

class AlarmManager @Inject constructor(private val activity: Activity) {

    private var alarmManager: AlarmManager =
        activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarm() {
        val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

        if (canScheduleExactAlarms) {
            val pendingIntent = createExactAlarmIntent()
            val alarmClockInfo =
                AlarmManager.AlarmClockInfo(1000, pendingIntent)
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
        }
    }

    private fun createExactAlarmIntent(): PendingIntent {
        val intent = Intent(activity, AlarmBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            activity,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}