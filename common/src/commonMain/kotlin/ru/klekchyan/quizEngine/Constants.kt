package ru.klekchyan.quizEngine

import kotlinx.datetime.Instant

val Instant.Companion.NONE
    get() = fromEpochMilliseconds(Long.MIN_VALUE)
