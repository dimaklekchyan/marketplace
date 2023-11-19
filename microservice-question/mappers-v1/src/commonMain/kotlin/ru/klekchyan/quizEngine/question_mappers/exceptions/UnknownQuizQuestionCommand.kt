package ru.klekchyan.quizEngine.question_mappers.exceptions

import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand


class UnknownQuizQuestionCommand(command: QuizQuestionCommand) : Throwable("Wrong command $command at mapping toTransport stage")
