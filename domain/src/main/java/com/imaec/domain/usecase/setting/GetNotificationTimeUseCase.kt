package com.imaec.domain.usecase.setting

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.SettingRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotificationTimeUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Flow<Triple<Boolean, Int, Int>>>(dispatcher) {

    override suspend fun execute(): Flow<Triple<Boolean, Int, Int>> {
        return settingRepository.getNotificationTime().map {
            val (amPm, hourMinute) = it.split(" ")
            val (hour, minute) = hourMinute.split(":")
            Triple(amPm == "오전", hour.toInt(), minute.toInt())
        }
    }
}
