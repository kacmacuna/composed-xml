package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName

class ColorAttributeHardCoded(
    private val input: String
) : ColorAttribute {
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