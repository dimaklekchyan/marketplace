package ru.klekchyan.quizEngine.question_mappers.v1.exceptions

import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand


class UnknownQuizQuestionCommand(command: QuizQuestionCommand) : Throwable("Wrong command $command at mapping toTransport stage")
