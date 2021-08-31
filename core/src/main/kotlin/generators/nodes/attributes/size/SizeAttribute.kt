package generators.nodes.attributes.size

import com.squareup.kotlinpoet.CodeBlock
import org.w3c.dom.Element

class DPAttribute(
    private val attributeName: String,
    private val element: Element
) {

    fun argument(): CodeBlock {
        val attribute = element.getAttribute(attributeName).ifEmpty {
            return CodeBlock.of("")
        }.removeSuffix("dp").toDouble()
        return CodeBlock.builder()
            .add(
                "$attribute.%M",
                ServiceLocator.get().imports.attributeImports.dp
            ).build()
    }

}