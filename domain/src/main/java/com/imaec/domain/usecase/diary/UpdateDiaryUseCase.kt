package com.imaec.domain.usecase.diary

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<DiaryDto, Int>(dispatcher) {

    override suspend fun execute(parameters: DiaryDto): Int {
        return diaryRepository.updateDiary(parameters)
    }
}
