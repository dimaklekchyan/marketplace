package sql.dsl

@DslMarker
annotation class SqlSelectDslRoot

@DslMarker
annotation class SqlSelectDsl

@SqlSelectDslRoot
fun query(block: SqlSelectBuilder.() -> Unit): String = SqlSelectBuilder().apply(block).build()

@SqlSelectDsl
class SqlSelectBuilder {
    private var select: String = "*"
    private var from: String? = null
    private var where: String = ""
    private var orderBy: String = ""

    @SqlSelectDsl
    fun select(vararg field: String) {
        select = field.joinToString(separator = ", ")
    }

    @SqlSelectDsl
    fun from(table: String) {
        from = table
    }

    @SqlSelectDsl
    fun where(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        where = ctx.build()
    }

    @SqlSelectDsl
    fun orderBy(vararg field: String) {
        orderBy = field.joinToString(separator = ", ")
    }

    fun build(): String {
        from?.let {
            val select = "select ${this.select}"
            val from = "from ${this.from}"
            val where = if(this.where.isNotEmpty()) "where ${this.where}" else ""
            val orderBy = if(this.orderBy.isNotEmpty()) "order by ${this.orderBy}" else ""
            return "$select $from $where $orderBy".trim()
        } ?: throw Exception()
    }
}

@SqlSelectDsl
class WhereContext: WhereBuilder() {
    @SqlSelectDsl
    fun or(block: OrContext.() -> Unit) {
        addCondition("(${OrContext().apply(block).build(WhereOperator.OR)})")
    }

    @SqlSelectDsl
    fun and(block: AndContext.() -> Unit) {
        addCondition("(${AndContext().apply(block).build(WhereOperator.AND)})")
    }
}

@SqlSelectDsl
class OrContext: WhereBuilder()

@SqlSelectDsl
class AndContext: WhereBuilder()

open class WhereBuilder {
    private val conditions = mutableListOf<String>()

    fun addCondition(condition: String) {
        conditions += condition
    }

    infix fun String.eq(another: Any?) {
        conditions += when (another) {
            is Number -> "$this = $another"
            is String -> "$this = '$another'"
            null -> "$this is null"
            else -> throw Exception("Wrong value: $another")
        }
    }

    infix fun String.nonEq(another: Any?) {
        conditions += when (another) {
            is Number -> "$this <> $another"
            is String -> "$this <> '$another'"
            null -> "$this is not null"
            else -> throw Exception("Wrong value: $another")
        }
    }

    fun build(operator: WhereOperator = WhereOperator.AND): String {
        return conditions.joinToString(" ${operator.separator} ")
    }

    enum class WhereOperator(val separator: String) {
        AND("and"),
        OR("or")
    }
}