package ru.otus.otuskotlin.marketplace.sql.dsl

@SqlSelectDsl
class WhereContext {
    val where: String = ""
    val or: () -> Unit = {} //OrContext
}

@SqlSelectDsl
class SqlSelectBuilder {
    private var select: Array<out String> = arrayOf("*")
    private var from: String? = null
    private var where: String = ""

    @SqlSelectDsl
    fun select(vararg field: String) {
        select = field
    }
    @SqlSelectDsl
    fun from(table: String) {
        from = table
    }
    @SqlSelectDsl
    fun where(block: WhereContext.() -> Unit) {
        val wcx = WhereContext().apply(block)

        where = wcx.where
    }

    fun build(): String {
        from?.let {
            val select = "select ${ this.select.joinToString(separator = ", ", postfix = "", prefix = "") }"
            val from = "from ${this.from}"
            val where = "where ${this.where}"
            return "$select $from $where"
        } ?: throw Exception()
    }
}

infix fun String.eq(another: String?): String = another?.let { "$this = '$it'" } ?: "$this is null"
infix fun String.nonEq(another: String?): String = another?.let { "$this != '$it'" } ?: "$this !is null"

infix fun String.eq(another: Number?): String = another?.let { "$this = $it" } ?: "$this is null"
infix fun String.nonEq(another: Number?): String = another?.let { "$this != $it" } ?: "$this !is null"

@SqlSelectDsl1
fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder = SqlSelectBuilder().apply(block)

@DslMarker
annotation class SqlSelectDsl

@DslMarker
annotation class SqlSelectDsl1