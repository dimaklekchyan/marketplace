package sql.dsl

@DslMarker
annotation class SqlSelectDslRootLevel

@DslMarker
annotation class SqlSelectDslFirstLevel

@DslMarker
annotation class SqlSelectDslSecondLevel

@SqlSelectDslRootLevel
fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder = SqlSelectBuilder().apply(block)

@SqlSelectDslFirstLevel
class SqlSelectBuilder {
    private var select: Array<out String> = arrayOf("*")
    private var from: String? = null
    private var where: String = ""

    @SqlSelectDslFirstLevel
    fun select(vararg field: String) {
        select = field
    }
    @SqlSelectDslFirstLevel
    fun from(table: String) {
        from = table
    }
    @SqlSelectDslFirstLevel
    fun where(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        where = ctx.where()
    }

    fun build(): String {
        from?.let {
            val select = "select ${ this.select.joinToString(separator = ", ") }"
            val from = "from ${this.from}"
            val where = if(this.where.isNotEmpty()) "where ${this.where}" else ""
            return "$select $from $where".trim()
        } ?: throw Exception()
    }
}

@SqlSelectDslSecondLevel
open class WhereContext: WhereBuilder() {
    override val operator: WhereOperator = WhereOperator.AND

    @SqlSelectDslSecondLevel
    fun or(block: OrContext.() -> Unit) {
        val ctx = OrContext().apply(block)
        where = ctx.where
    }
}

@SqlSelectDslSecondLevel
class OrContext: WhereBuilder() {
    override val operator: WhereOperator = WhereOperator.OR
}

open class WhereBuilder {
    open val operator: WhereOperator = WhereOperator.AND

    private  val elements = mutableListOf<String>()
    var where: () -> String = {
        elements.joinToString(
            separator = operator.separator,
            prefix = if (elements.size > 1) "(" else "",
            postfix = if (elements.size > 1) ")" else "",
        )
    }

    infix fun String.eq(another: String) = elements.add("$this = '$another'")
    infix fun String.nonEq(another: String) = elements.add("$this != '$another'")

    infix fun String.eq(another: Any?) = elements.add(another?.let { "$this = $it" } ?: "$this is null")
    infix fun String.nonEq(another: Any?) = elements.add(another?.let { "$this != $it" } ?: "$this !is null")

    enum class WhereOperator(val separator: String) {
        AND(" and "),
        OR(" or ")
    }
}