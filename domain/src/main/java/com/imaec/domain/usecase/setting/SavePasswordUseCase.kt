package com.imaec.domain.usecase.setting

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.SettingRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<String, Unit>(dispatcher) {

    override suspend fun execute(parameters: String) {
        return settingRepository.savePassword(parameters)
    }
}
