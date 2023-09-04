package ru.klekchyan.quizEngine.v1.exceptions

import ru.klekchyan.quizEngine.models.QuizCommand

class UnknownQuizCommand(command: QuizCommand) : Throwable("Wrong command $command at mapping toTransport stage")
