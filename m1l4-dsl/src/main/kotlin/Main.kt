package ru.otus.otuskotlin.marketplace

import ru.otus.otuskotlin.marketplace.user.dsl.buildUser

fun main() {
    val user = buildUser {
        name {
            first = "Dima"
            second = ""
            last = "Klekchyn"
        }
        contacts {
            email = ""
            phone = ""
        }
        availability {

        }
    }
}
