package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock

class ColorAttributeHardCoded(
    private val input: String
) : ColorAttribute {

    override fun argument(): CodeBlock {
        return CodeBlock.of(
            """%T(android.graphics.Color.parseColor("$input"))""".trimIndent(),
            ServiceLocator.get().imports.attributeImports.graphics
        )
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("android.graphics", "Color"),
        )
    }

    override fun isEmpty(): Boolean {
        return false
    }
}