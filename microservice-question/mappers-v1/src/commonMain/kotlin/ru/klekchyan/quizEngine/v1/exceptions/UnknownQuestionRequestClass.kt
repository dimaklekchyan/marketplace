package ru.klekchyan.quizEngine.v1.exceptions

import kotlin.reflect.KClass

class UnknownQuestionRequestClass(clazz: KClass<*>) : RuntimeException("Class $clazz cannot be mapped to QuizQuestionContext")
