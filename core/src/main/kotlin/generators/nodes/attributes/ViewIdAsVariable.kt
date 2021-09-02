package generators.nodes.attributes

@JvmInline
value class ViewIdAsVariable(
    private val value: String
) {
    override fun toString(): String {
        return value.mapIndexed { index: Int, c: Char ->
            if (index != 0 && value[index - 1] == '_')
                c.uppercaseChar()
            else
                c
        }.toString().filter { c ->
            c != '_' && c != ',' && c != ']' && c != '[' && c != ' '
        }.replaceFirstChar { it.lowercase() }
    }
}