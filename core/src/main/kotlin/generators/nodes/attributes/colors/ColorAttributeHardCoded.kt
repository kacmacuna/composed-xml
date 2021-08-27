package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock

class ColorAttributeHardCoded(
    private val input: String
) : ColorAttribute {
    override fun statement(): String {
        return """Color(android.graphics.Color.parseColor("$input"))""".trimIndent()
    }



    override fun argument(): CodeBlock {
        return CodeBlock.of(
            """%T(android.graphics.Color.parseColor("$input"))""".trimIndent(),
            GenerationEngine.get().className("androidx.compose.ui.graphics")
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