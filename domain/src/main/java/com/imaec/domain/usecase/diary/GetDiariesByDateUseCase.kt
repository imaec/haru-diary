package com.imaec.domain.usecase.diary

import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.repository.DiaryRepository
import javax.inject.Inject

class GetDiariesByDateUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    operator fun invoke(date: String): List<DiaryDto> {
        return diaryRepository.getDiariesByDate(date)
    }
}
