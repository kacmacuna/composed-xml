package generators.nodes.attributes.size

import com.squareup.kotlinpoet.CodeBlock
import org.w3c.dom.Element

class DPAttribute(
    private val attributeValue: String,
) {

    fun isValid(): Boolean {
        return attributeValue.isNotEmpty()
    }

    fun statement(): CodeBlock {
        return if (isResource())
            resourceArgument()
        else
            hardCodedArgument()
    }

    fun isResource(): Boolean {
        return attributeValue.contains("@dimen")
    }

    fun resourceValue(): String {
        return attributeValue.removePrefix("@dimen/")
    }

    private fun hardCodedArgument(): CodeBlock {
        return CodeBlock.of("${attributeValue.removeSuffix("dp")}.%M", ServiceLocator.get().imports.attributeImports.dp)
    }

    private fun resourceArgument(): CodeBlock {
        return CodeBlock.of(
            "%M(id = R.dimen.${attributeValue.removePrefix("@dimen/")})",
            ServiceLocator.get().imports.attributeImports.dimenResource
        )
    }

}