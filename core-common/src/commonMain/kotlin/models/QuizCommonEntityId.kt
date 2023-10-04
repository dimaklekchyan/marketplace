package models

import kotlin.jvm.JvmInline

@JvmInline
value class QuizCommonEntityId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QuizCommonEntityId("")
    }
}
