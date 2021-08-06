package generators.nodes.elements.colors

import com.squareup.kotlinpoet.ClassName

class ColorElementHardCoded(
    private val input: String
) : ColorElement {
    override fun statement(): String {
        return """Color(android.graphics.Color.parseColor("$input"))""".trimIndent()
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