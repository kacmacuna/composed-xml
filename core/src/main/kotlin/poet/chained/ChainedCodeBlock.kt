package poet.chained

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class ChainedCodeBlock(
    private val prefixNamedParam: String,
    private val prefix: MemberName,
    vararg val attributes: ChainedMemberName
) {

    fun codeBlock(): CodeBlock {
        return CodeBlock.of(
            "$prefixNamedParam = %M.${
                chained().ifEmpty {
                    return CodeBlock.of("")
                }
            }",
            prefix
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


}