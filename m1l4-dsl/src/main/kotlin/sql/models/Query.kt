package ru.otus.otuskotlin.marketplace.sql.models

@JvmInline
value class Query2(
    val query: String
)

data class Query(
    val select: List<String>,
    val from: String,
)