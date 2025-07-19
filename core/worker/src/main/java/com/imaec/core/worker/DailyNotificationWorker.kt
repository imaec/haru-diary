package com.imaec.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.imaec.core.utils.notification.NotificationHelper
import com.imaec.core.utils.utils.formatDate
import com.imaec.domain.usecase.diary.GetDiariesByDateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Date

@HiltWorker
class DailyNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper,
    private val getDiariesByDateUseCase: GetDiariesByDateUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val date = Date().formatDate("yyyy-MM-dd")
        getDiariesByDateUseCase(date = date).ifEmpty {
            notificationHelper.sendDailyNotification()
        }
        return Result.success()
    }
}
