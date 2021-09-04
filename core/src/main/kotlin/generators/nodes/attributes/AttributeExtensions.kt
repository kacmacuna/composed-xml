package generators.nodes.attributes

fun String.xmlValueAsVariable() = mapIndexed { index: Int, c: Char ->
    if (index != 0 && this[index - 1] == '_')
        c.uppercaseChar()
    else
        c
}.toString().filter { c ->
    c != '_' && c != ',' && c != ']' && c != '[' && c != ' '
}.replaceFirstChar { it.lowercase() }