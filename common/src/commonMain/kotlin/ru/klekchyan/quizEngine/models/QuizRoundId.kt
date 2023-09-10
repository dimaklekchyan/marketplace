package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizRoundId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = QuizRoundId(0)
    }
}