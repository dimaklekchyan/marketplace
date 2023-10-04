package ru.klekchyan.quizEngine.v1.exceptions

import kotlin.reflect.KClass

class UnknownRoundRequestClass(clazz: KClass<*>) : RuntimeException("Class $clazz cannot be mapped to QuizRoundContext")
