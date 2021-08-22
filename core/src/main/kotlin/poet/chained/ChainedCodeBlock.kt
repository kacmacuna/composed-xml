package poet.chained

import com.squareup.kotlinpoet.CodeBlock

class ChainedCodeBlock(
    private val prefix: String,
    vararg val attributes: ChainedMemberCall
) {

    fun codeBlock(): CodeBlock {
        return CodeBlock.of(
            "$prefix${
                chained().ifEmpty {
                    return CodeBlock.of("")
                }
            }"
        )
    }

    private fun chained(): String {
        val filtered = attributes.filter { it.prefixContainsArguments || it.argument.isNotEmpty() }.filter { it.prefix.isNotEmpty() }
        return filtered.map {
            val suffix = if (it.prefix != filtered.last().prefix) "." else ""
            val chainedBuilder = StringBuilder()

            chainedBuilder.append(it.prefix)
            if (it.prefixContainsArguments.not()) chainedBuilder.append("(${it.argument})")
            if (suffix.isNotEmpty()) chainedBuilder.append(suffix)

            chainedBuilder.toString()
        }.joinToString("") { it }
    }

    companion object {
        const val IGNORE_EMPTY = "IGNORE_EMPTY"
    }


}