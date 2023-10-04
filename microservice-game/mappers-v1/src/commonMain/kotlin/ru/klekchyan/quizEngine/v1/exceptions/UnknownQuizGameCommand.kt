package ru.klekchyan.quizEngine.v1.exceptions

import ru.klekchyan.quizEngine.models.QuizGameCommand


class UnknownQuizGameCommand(command: QuizGameCommand) : Throwable("Wrong command $command at mapping toTransport stage")
