package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizGameId(val id: Int) {

    fun asString() = id.toString()

    companion object {
        val NONE = QuizGameId(0)
    }
}