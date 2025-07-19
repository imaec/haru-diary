package com.imaec.core.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val DAILY_NOTIFICATION_WORK_ID = "DailyNotification"

class DailyNotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun schedule(hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val target = (now.clone() as Calendar).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (now.after(this)) add(Calendar.DAY_OF_YEAR, 1)
        }
        val initialDelay = target.timeInMillis - now.timeInMillis
        val request = PeriodicWorkRequestBuilder<DailyNotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            DAILY_NOTIFICATION_WORK_ID,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelSchedule() {
        WorkManager.getInstance(context).cancelUniqueWork(DAILY_NOTIFICATION_WORK_ID)
    }
}
