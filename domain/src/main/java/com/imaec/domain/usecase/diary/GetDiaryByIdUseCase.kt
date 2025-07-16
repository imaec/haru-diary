package com.imaec.domain.usecase.diary

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDiaryByIdUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Long, DiaryDto?>(dispatcher) {

    override suspend fun execute(parameters: Long): DiaryDto? {
        return diaryRepository.getDiaryById(parameters)
    }
}
