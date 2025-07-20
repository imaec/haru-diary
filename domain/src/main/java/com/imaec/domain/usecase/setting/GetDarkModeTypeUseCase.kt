package com.imaec.domain.usecase.setting

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.repository.SettingRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkModeTypeUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Flow<DarkModeType>>(dispatcher) {

    override suspend fun execute(): Flow<DarkModeType> {
        return settingRepository.getDarkModeType()
    }
}
