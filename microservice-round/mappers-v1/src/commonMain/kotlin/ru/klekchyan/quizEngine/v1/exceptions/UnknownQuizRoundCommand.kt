package ru.klekchyan.quizEngine.v1.exceptions

import ru.klekchyan.quizEngine.models.QuizRoundCommand


class UnknownQuizRoundCommand(command: QuizRoundCommand) : Throwable("Wrong command $command at mapping toTransport stage")
