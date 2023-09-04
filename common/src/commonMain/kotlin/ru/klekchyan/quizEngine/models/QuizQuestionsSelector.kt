package ru.klekchyan.quizEngine.models

data class QuizQuestionsSelector(
    val gameId: QuizGameId = QuizGameId.NONE,
    val roundId: QuizRoundId = QuizRoundId.NONE
)
