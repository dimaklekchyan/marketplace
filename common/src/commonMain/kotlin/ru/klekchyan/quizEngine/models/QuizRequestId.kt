package ru.klekchyan.quizEngine.models

import kotlin.jvm.JvmInline
@JvmInline
value class QuizRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QuizRequestId("")
    }
}