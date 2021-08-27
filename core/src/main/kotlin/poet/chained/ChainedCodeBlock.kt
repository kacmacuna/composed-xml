package poet.chained

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.joinToCode

class ChainedCodeBlock(
    private val prefixNamedParam: String,
    private val prefix: ClassName,
    vararg val attributes: ChainedMemberName
) {

    fun codeBlock(): CodeBlock {
        return CodeBlock.of(
            "$prefixNamedParam = %T.%L",
            prefix, chained().ifEmpty { return CodeBlock.of("") }.joinToCode(separator = ".")
        )
    }

    private fun chained(): List<CodeBlock> {
        val filtered = attributes.filter { it.containsArguments.not() || it.argument.isNotEmpty() }
            .filter { it.prefix.simpleName.isNotEmpty() }
        return filtered.map {
            val chainedBuilder = CodeBlock.builder()

            if (it.containsArguments) {
                chainedBuilder.add("%M(${it.argument})", it.prefix)
            } else {
                chainedBuilder.add("%M()", it.prefix)

            }

            chainedBuilder.build()
        }
    }


}