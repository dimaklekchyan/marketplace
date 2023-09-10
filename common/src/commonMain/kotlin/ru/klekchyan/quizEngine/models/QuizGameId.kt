package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizGameId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = QuizGameId(0)
    }
}