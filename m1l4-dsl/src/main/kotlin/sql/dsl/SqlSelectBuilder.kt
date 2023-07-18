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
    fun where(block: WhereBuilder.() -> Unit) {
        where = WhereBuilder().apply(block).build()
    }

    @SqlSelectDsl
    fun orderBy(vararg field: String) {
        orderBy = field.joinToString(separator = ", ")
    }

    fun build(): String {
        from?.let {
            val select = getSelect()
            val from = getFrom()
            val where = getWhere()
            val orderBy = getOrderBy()
            return "$select $from $where $orderBy".trim()
        } ?: throw Exception()
    }

    private fun getSelect() = "$SELECT $select"
    private fun getFrom() = "$FROM $from"
    private fun getWhere() = if(where.isNotEmpty()) "$WHERE $where" else ""
    private fun getOrderBy() = if(orderBy.isNotEmpty()) "$ORDER_BY $orderBy" else ""

    companion object {
        private const val SELECT = "select"
        private const val FROM = "from"
        private const val WHERE = "where"
        private const val ORDER_BY = "order by"
    }
}

@SqlSelectDsl
class WhereBuilder {
    private val conditions = mutableListOf<String>()

    infix fun String.eq(another: Any?) {
        conditions += when (another) {
            is Number -> "$this = $another"
            is String -> "$this = '$another'"
            null -> "$this is null"
            else -> throw IllegalArgumentException()
        }
    }

    infix fun String.nonEq(another: Any?) {
        conditions += when (another) {
            is Number -> "$this != $another"
            is String -> "$this != '$another'"
            null -> "$this !is null"
            else -> throw IllegalArgumentException()
        }
    }

    @SqlSelectDsl
    fun or(block: WhereBuilder.() -> Unit) {
        val orCondition = WhereBuilder().apply(block).build(WhereOperator.OR)
        conditions += "($orCondition)"
    }

    @SqlSelectDsl
    fun and(block: WhereBuilder.() -> Unit) {
        val andCondition = WhereBuilder().apply(block).build(WhereOperator.AND)
        conditions += "($andCondition)"
    }

    fun build(operator: WhereOperator = WhereOperator.AND): String {
        return conditions.joinToString(" ${operator.separator} ")
    }

    enum class WhereOperator(val separator: String) {
        AND("and"),
        OR("or")
    }
}