package com.imaec.domain.usecase.diary

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiariesCountUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Flow<Int>>(dispatcher) {

    override suspend fun execute(): Flow<Int> {
        return diaryRepository.getDiariesCount()
    }
}
