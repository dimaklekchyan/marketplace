package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizRoundId(val id: Int) {

    fun asString() = id.toString()

    companion object {
        val NONE = QuizRoundId(0)
    }
}