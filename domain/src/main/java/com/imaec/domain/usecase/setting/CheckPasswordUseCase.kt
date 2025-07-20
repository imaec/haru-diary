package com.imaec.domain.usecase.setting

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.SettingRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<String, Boolean>(dispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return settingRepository.getPassword() == parameters
    }
}
