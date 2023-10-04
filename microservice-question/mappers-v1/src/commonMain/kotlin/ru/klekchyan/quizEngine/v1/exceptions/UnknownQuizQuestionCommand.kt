package ru.klekchyan.quizEngine.v1.exceptions

import ru.klekchyan.quizEngine.models.QuizQuestionCommand


class UnknownQuizQuestionCommand(command: QuizQuestionCommand) : Throwable("Wrong command $command at mapping toTransport stage")
