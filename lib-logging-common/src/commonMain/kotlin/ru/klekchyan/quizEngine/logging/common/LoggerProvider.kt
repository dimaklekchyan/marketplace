package ru.klekchyan.quizEngine.logging.common

import kotlin.reflect.KClass

class LoggerProvider(
    private val provider: (String) -> ILogWrapper = { ILogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
}
