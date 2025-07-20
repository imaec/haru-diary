package com.imaec.domain.usecase.setting

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.repository.SettingRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveDarkModeTypeUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<DarkModeType, Unit>(dispatcher) {

    override suspend fun execute(parameters: DarkModeType) {
        return settingRepository.saveDarkModeType(parameters)
    }
}
