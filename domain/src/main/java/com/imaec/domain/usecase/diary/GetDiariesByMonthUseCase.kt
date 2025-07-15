package com.imaec.domain.usecase.diary

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiariesByMonthUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<String, Flow<List<DiaryDto>>>(dispatcher) {

    override suspend fun execute(parameters: String): Flow<List<DiaryDto>> {
        return diaryRepository.getDiariesByMonth(parameters)
    }
}
