package com.imaec.domain.usecase.diary

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Long, Int>(dispatcher) {

    override suspend fun execute(parameters: Long): Int {
        return diaryRepository.deleteDiaryById(parameters)
    }
}
