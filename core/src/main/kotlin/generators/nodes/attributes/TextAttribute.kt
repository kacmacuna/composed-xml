package generators.nodes.attributes

import com.squareup.kotlinpoet.CodeBlock

class TextAttribute(
    private val attributeValue: String,
) {

    fun isNotEmpty(): Boolean {
        return attributeValue.isNotEmpty()
    }

    fun statement(): CodeBlock {
        return if (attributeValue.contains("@string"))
            resourceArgument()
        else
            hardCodedArgument()
    }

    private fun hardCodedArgument(): CodeBlock {
        return if (attributeValue.isNotEmpty())
            CodeBlock.of("\"$attributeValue\"")
        else
            CodeBlock.of("\"\"")
    }

    private fun resourceArgument(): CodeBlock {
        return CodeBlock.of(
            "%M(id = R.string.${attributeValue.removePrefix("@string/")})",
            ServiceLocator.get().imports.attributeImports.stringResource
        )
    }

}