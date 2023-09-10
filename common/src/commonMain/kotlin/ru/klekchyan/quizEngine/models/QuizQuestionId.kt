package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizQuestionId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = QuizQuestionId(0)
    }
}