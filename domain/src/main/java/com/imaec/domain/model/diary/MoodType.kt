package com.imaec.domain.model.diary

enum class MoodType(val emoji: String) {
    HAPPY(emoji = "😄"),
    BAD(emoji = "😕"),
    ANGRY(emoji = "😡"),
    EXCITED(emoji = "😆"),
    SAD(emoji = "😢"),
    RELAXED(emoji = "😌"),
    TIRED(emoji = "🥱"),
    NORMAL(emoji = "😐");

    companion object {
        fun toSelectableList(): List<Pair<MoodType, Boolean>> =
            MoodType.entries.mapIndexed { index, moodType ->
                moodType to (index == 0)
            }
    }
}
