package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizQuestionId(val id: Int) {

    fun asString() = id.toString()

    companion object {
        val NONE = QuizQuestionId(0)
    }
}